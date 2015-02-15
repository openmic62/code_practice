#!C:/Perl/bin/perl.exe
# c:/bin/keywords.pl -d kwv-kwp-parsed-sql-args-cont siteID=1 pageID=1 from_file=1 whole_doc=1
# c:/bin/keywords.pl -d kwv-kwp-parsed-sql-args-cont siteID=1 pageID=1 ep_check="c:\sites\ep-check-item.txt" from_file=1 whole_doc=1

use strict;
use vars( qw/$opt_d $opt_e $opt_h/ );
# http://www.conniesworld.com/publishing_fga.php
# wwwgate0.mot.com 1080
# 127.0.0.1;*.mot.com;localhost
use Cwd;
use Data::Dumper;
use DBI;
use File::Basename;
use Getopt::Std;

################################################################################
#                                                                              #
#                           Global Variables                                   #
#                                                                              #
################################################################################
	
#### Scalars
my $DB          = "";  # the name of the MySQL database to which we want to connect
my $DBH         = "";  # MySQL database handle - holds db connection object
my $DSN         = "";  # full qualified data source name used to make a MySQL db connection
my $HOST        = "";  # the host on which our MySQL server is running.
my $PASSWORD    = "";  # MySQL password for this connection
my $TOTAL_WORDS = 0;   # 050426 - holds the total number of words seen during parsing
my $WHOAMI      = "";  # the user connecting to the MySQL database

my $USAGE = "

usage: $0 -h | [-d <debug_flag>] -e <sub> <field_name=field_value> [<field_name1=field_value2> ... <field_nameN=field_valueN]

       -d (optional) turns on various debug output; you can also think of this a verbose mode
       -e (required) this option tells the script what subroutine <sub> to execute
          Values: xxxx     - reserved for future use
                  xxxx     - reserved for future use
       -h (optional) show this help information
       
       Examples:
         To get this help page:
           ./keywords.pl -h

         To make a connection to a database \"test\" and a host \"127.0.0.1\"
           ./keywords.pl -e xxxx db=test host=127.0.0.1
           
         Valid field=value pairs
           db          = <database_name>             e.g. test
           host        = <sql_server_host>           e.g. localhost, 127.0.0.1
           pageID      = <primary_key_of_pages_recs> e.g. 1234
           siteID      = <primary_key_of_sites_recs> e.g. 1234
           ep_check    = <temp_file_name>            e.g. c:\sites\ep-check-item.txt
           whole_doc   = <boolean>                   e.g. 1 or 0 - defaults to 0
                                                          1 means parse the entire contents of the file
                                                          0 means parse only the contents between delimiters
           from_file   = <boolean>                   e.g. 1 or 0 - defaults to 0
                                                          1 means retrieve contents to parse from file
                                                          0 means retrieve contents to parse from database
           php_out     = <boolean>                   e.g. 1 or 0 - defaults to 1
                                                          1 means output parsing results to php API via STDOUT
                                                          0 means omit formating to php API
          
";

#### Arrays
# an array for parsed results
my @PR       = ();

#### Hashes
# error- handling attributes for MySQL connection
my %ATTR     = (PrintError => 0,
						    RaiseError => 0);

# a hash we use to manage filenames and file content
my %CONTENT  = ('file_contents_original' => "",
								'file_contents_analyzed' => "",
								'file_name_original'     => "car_cd_players.htm",
								'file_name_analyzed'     => "car_cd_players_analyzed.htm",
								);

# a hash for general purpose use
my %G        = ();

# a hash for keywords
my %KEYWORDS = ();

#### from Perl Cookbook p. 280
my %SEEN     = ();

################################################################################
#                                                                              #
#                              Main Logic                                      #
#                                                                              #
################################################################################
getopts('d:e:h');

# Show usage if requested by a command line option or no arguments are supplied
die $USAGE if $opt_h;

##### set default values for general purpose scalars
$G{'pageID'} = 1;
##### bring in the command line arguments
foreach (@ARGV) {
    # create some scalars into which we can parse the args
    my $name  = "";
    my $value = "";
    # parse the args
    ($name, $value) = m/(.+?)=(.+?)$/;
    # save the arg
    $G{$name} = $value;
}
# print debug info
print Data::Dumper->Dump( [\%G], [qw(command_line_args)] ) if $opt_d =~ /args/;

##### <mlr 050214: begin - integrate with web_pub database>
# formulate data source name
$DB       = $G{'db'}   ? $G{'db'}     : "web_pub";
$HOST     = $G{'host'} ? $G{'host'}   : "localhost";
$DSN      = "DBI:mysql:$DB:$HOST";
$WHOAMI   = "root";
$PASSWORD = "roc00mik";

# connect to database
$DBH = DBI->connect ($DSN, $WHOAMI, $PASSWORD, \%ATTR) 
	or print STDERR "Cannot make connection to database: $DBI::err ($DBI::errstr)\n\n" and exit;

# pull the keywords from my database
%KEYWORDS = get_keywords_from_database($opt_d, $DBH, $G{'pageID'});

# pull the page details from my database
%CONTENT = get_page_details_from_database($opt_d, $DBH, $G{'pageID'}, $G{'siteID'}, %CONTENT);
##### <mlr 050214: end - integrate with web_pub database>

##### <mlr 050215: see bone yard for code cut from here>

if ( $G{'from_file'} ) { # <mlr 050218: begin - read contents from file>
	#### read in the file we want to parse
	# save the filename key
	my $fname = $CONTENT{file_name_original};
		
	# declare array to hold contents of files we read in
	my @f_c = ();
		
	if ( -e $fname ) {
		##### execute here if the file DOES exist
		# print debug info
		print "File exists: $fname\n" if $opt_d =~ /exist/; # -d exist
			
		# read in the original htm file
		open (IN, "<$fname");
		if ( $G{'whole_doc'} ) {
			@f_c = <IN>;
		} else {
			my $saw_start_tag = 0;
			while ( <IN> ) {
				if ( m%<content>begin</content>% ) {
					$saw_start_tag = 1;
					push @f_c, ($_);
				} elsif ( m%<content>end</content>% ){
					$saw_start_tag = 0;
					push @f_c, ($_);
				} elsif ( $saw_start_tag ) {
					push @f_c, ($_);
				}
				next;
			}
		}
		close "IN";
		
		# print debug info
		print "\$#f_c-->$#f_c<--\n" if $opt_d =~ /lines/;

		# print debug info
		print Data::Dumper->Dump([\%SEEN], [qw(words_seen_in_file)]) if $opt_d =~ /seen/; # -d hash httpd
		#### end from Perl Cookbook p. 280   #################################################
	
		# construct the file contents key
		my $fcont = $fname;
		$fcont =~ s/name/contents/;

		# put the file contents into the hash
		#$CONTENT{file_contents_original} = [@f_c[0..4]]; # I used a slice to shorten the debug output
		$CONTENT{file_contents_original} = \@f_c;
			
	} else {
		# print debug info
		print "File does not exist: $CONTENT{$fname}\n";
		##### execute here if the file DOES NOT exist
	};
} # <mlr 050218: end - read contents from file>
  elsif ( $G{'ep_check'} ) { # <mlr 050218: begin - read contents from Elance project web app>
	# declare array to hold contents of file we read in
	my @f_c = ();
		
	##### execute here if the file DOES exist
	# print debug info
	print "File exists: $G{'ep_check'}\n" if $opt_d =~ /cont/; # -d exist
		
	# read in the original htm file
	open (IN, "<$G{'ep_check'}");
	@f_c = <IN>;
	close "IN";
	
 	# put the webb app contents into the hash
	##$CONTENT{file_contents_original} = [split /\\n/, $G{'content'}];
	$CONTENT{file_contents_original} = \@f_c;
} # <mlr 050218: end - read contents from Elance project web app>
	
# print debug info
print Data::Dumper->Dump([\%CONTENT], [qw(CONTENT_after_read)]) if $opt_d =~ /cont/; # -d hash httpd

# print debug info
print "\$#\$CONTENT{file_contents_original}-->$#{$CONTENT{file_contents_original}}<--\n" if $opt_d =~ /cont/;

##### parse all the single words from the content using code from the Perl Cookbook
##### this sub directly modifies the global hash %SEEN
##### we use the results in %SEEN to sanity check what we get from my own parsing
##### code in sub parse_either_traditional_or_kwv_from_content
parse_single_words_from_content($opt_d, $CONTENT{file_contents_original});

##### parse each of the keywords from the content
foreach ( sort keys %KEYWORDS ) {
	# skip the keyword variatons now; do them in the next loop
	next if /keyword_vars|t2-secondary/; # <mlr 050505: implement list_use>
	# strip off the hash key ordering characters
	my $kw_type = $_;
	$kw_type =~ s/^\d\d//;
	# print debug info (this prints traditional keyword for separation in the output)
	print "-" x 80 . "\n$kw_type -->$KEYWORDS{$_}<--\n" if $opt_d =~ /kwp/;
	# parse out the main keywords
	push @PR, {parse_either_traditional_or_kwv_from_content($opt_d, $KEYWORDS{$_}, $kw_type)};
	
	# print debug info
	print "\%SEEN{$KEYWORDS{$_}}-->$SEEN{$KEYWORDS{$_}}<--\n" 	#### from Perl Cookbook p. 280
		if $opt_d =~ /this_is_erroneous_but_I_want_to_keep_it_for_possible_debug/;
		# this print output is invalid because the perl cookbook code parses only single words NOT phrases
}
# <mlr 050505 - BEGIN: implement list_use>
##### parse each of the keyword variations from the content
foreach ( @{$KEYWORDS{'03t2-secondary'}} ) {
	# print debug info (this prints t2-secondary keywords for separation in the output)
	print "-" x 80 . "\nt2-secondary -->$_<--\n" if $opt_d =~ /t2s/;
	# parse out the t2-secondary keywords
	push @PR, {parse_either_traditional_or_kwv_from_content($opt_d, $_, 't2-secondary')};

	# print debug info
	print "\%SEEN{$_}-->$SEEN{$_}<--\n" 	#### from Perl Cookbook p. 280
		if $opt_d =~ /t2s/;
}
# <mlr 050505 - END: implement list_use>
##### parse each of the keyword variations from the content
foreach ( @{$KEYWORDS{'04keyword_vars'}} ) {
	# print debug info (this prints keyword variations for separation in the output)
	print "-" x 80 . "\nkeyword_variation -->$_<--\n" if $opt_d =~ /kwv/;
	# parse out the keyword variations
	push @PR, {parse_either_traditional_or_kwv_from_content($opt_d, $_, 'keyword_vars')};

	# print debug info
	print "\%SEEN{$_}-->$SEEN{$_}<--\n" 	#### from Perl Cookbook p. 280
		if $opt_d =~ /kwv/;
}

##### <mlr 050426 - BEGIN: add total words seen to PHP output so that I can calculate keyword density>
##### <mlr 060721 - I need to omit my HTML comment delimiters from %SEEN. I'm getting 19 TOTAL WORDS
#####               with my default content. That's 6.33% of 300 words, a significant error.
{
  foreach ( keys %SEEN ) {
  	# <mlr 060724: begin - omit HTML tags from total word count>
  	next if m/^p$/;
  	next if m/^h1$/;
  	next if m/^h2$/;
  	# <mlr 060724: end - omit HTML tags from total word count>
  	$TOTAL_WORDS += $SEEN{$_};
  }
  
  # must take care to change PERL undefs to 0 so that PHP serialization succeeds
  $G{"from_file"} = ($G{"from_file"} == undef) ? 0 : $G{"from_file"};
  $G{"whole_doc"} = ($G{"whole_doc"} == undef) ? 0 : $G{"whole_doc"};
  $G{"debug"}     = ($G{"debug"}     == undef) ? 0 : $G{"debug"};
  
  # prepend @PR with the TOTAL_WORDS, "from_file", "whole_doc", "debug"
  unshift @PR, ($TOTAL_WORDS, $G{"from_file"}, $G{"whole_doc"}, $G{"debug"});
}
# <mlr 050426 - END: add total words seen to PHP output so that I can calculate keyword density>

# print debug info (look at parsing results)
print Data::Dumper->Dump([\@PR], [qw(PR)]) if $opt_d =~ /PR/; # -d hash httpd

print serialize_parsing_results_for_PHP($opt_d, \@PR);

################################################################################
#                                                                              #
#                           Subroutine Definitions                             #
#                                                                              #
################################################################################
###############################################################################
## NAME:
##     get_keywords_from_database
## INPUT PARAMETERS:
##     $d                     = $d holds value of $opt_d as passed in from the command line
##                              (e.g.: 'kwv-kwp-parsed-sql-args-cont')
##     $dbh                   = $dbh holds MySQL db connection object
##                              (e.g.: this is a reference to an object)
##     $pid                   = $pid holds the primary key for the web site page
##                              (e.g.: 1)
## OUTPUT PARAMETERS:
##     none
## RETURN VALUES:
##     Success = returns a lexical hash to be assigned to global hash (%KEYWORDS) that
##               contains keywords I want to parse from the page's content
##     Failure = n/a
## DESCRIPTION:
##     I created a MySQL database to manage the keywords I get from WordTracker
##     (http://www.wordtracker.com/). James Martell teaches a flat list method
##     to manage these keywords. I used his approach at first, but found it lacking.
##     The keyword searches comprise a large amount of effort toward a successful
##     affiliate marketing site. I saw myself losing a lot of the value from this effort
##     by failing to capture the thought processes I used during keyword list compilation.
##     I employed Excel to mitigate the loss, but found that lacking as well. I
##     decided to bite the bullet and implement a database system that will do
##     What I want.
##
##     <mlr 050425: begin>
##     I discovered another huge reason for "biting the bullet". James Martell says
##     to create a 150-300 word keyword list comprised of words/phrases from WordTracker.
##     He then explains, in great detail, how to construct product pages based on words
##     from this list. Finally, he describes, in one paragraph (p 154a), how to write
##     keyword based content using keywords from the list.
##     
##     But, the real money making work lies in creating thousands of keyword based article 
##     pages. He mentions this only very briefly in the 2002 manual (p. 180b, 231a, etc).
##     (He does consistently empahsize, in the Buzz, the need for adding more and more content.)
##     This really means managing the 150-300 word list: keeping track of which words
##     I used on which page as primaries; which words did I use as T1S or T2S; parsing
##     primaries, T1S, T2s, etc. into keyword variations; gathering stats on what combos
##     produces the best results; etc. I could probably think of a dozen more things to 
##     consider.
##
##     Basically, to make this thing work like I envision, I must have an efficient and effective 
##     system, and/or set of tools, to manage and track the keywords AS I USE THEM to 
##     create content. He provides few suggestions in the manual. So, by "biting the bullet",
##     I created a sytem and set of tools that provide comprehensive managerially
##     capability as I want.
##     <mlr 050425: end>
##     
## 
##     This sub retrieves a set of keywords from the web_pub database. I chose 
##     that set of keywords from the WordTracker list mentioned above. Each web page
##     shall have such a set of keywords. I need this set of keywords in memory so
##     that I can use them in the code that parses web page content. Parsing the
##     content allows me to ensure I have optimized the content for search engine
##     spiders.    
##     
###############################################################################
sub get_keywords_from_database {
	#### bring in subroutine arguments
	my ( $d, $dbh, $pid ) = @_;                           # $d holds value of $opt_d as passed in from the command line
	                                                      # $dbh holds MySQL db connection object
	                                                      # $pid holds the primary key for the web site page 
	#### set up lexical scalars
	my $result_set_a = "";                                # ref to an array containing arrays representing records returned
	my $sql_stmt     = "";                                # put an SQL statement to execute in this scalar
	
	#### set up lexical hashes
	my %keywords     = ();                                # put the keywords retrieved from the database here
	
	# print debug info
	print "-" x 80 . "\n"                                        if $opt_d =~ /sql/;
	print "From sub get_keywords_from_database\n"                if $opt_d =~ /sql/;

	#### get the page record that has this page_id primary key
	# form the sql
	$sql_stmt = qq(select * from list_use where page_id=$pid order by lu_used_as);
	# print debug info
	print "$sql_stmt\n"                                          if $opt_d =~ /sql/;
	# execute the query
	$result_set_a = $DBH->selectall_arrayref($sql_stmt)
		or die "keywords.pl::Cannot execute query: $DBI::err ($DBI::errstr)";
		
	# <mlr 050504 - BEGIN: implement list_use table for keywords>
	{
		# initialize the lexical hash to receive the t2-secondary keywords
		$keywords{'03t2-secondary'} = [];
		# loop through each keyword for this page that was returned from the list_use table
		# and store it in its spot in the lexical hash
		foreach (@$result_set_a) {
			# form the nested SQL
			$sql_stmt = qq(select wtkw_keyword from wt_keywords where wtkw_id=$_->[3]);
			# print debug info
			print "$sql_stmt\n"                                          if $opt_d =~ /sql/;
			# execute the query
			my $kw_result_set_a = $DBH->selectall_arrayref($sql_stmt)
				or die "keywords.pl::Cannot execute query: $DBI::err ($DBI::errstr)";
			# print debug info
			print Data::Dumper->Dump([$kw_result_set_a], [qw(kw_result_set_a)]) if $opt_d =~ /sql/;
			# put the actual keyword into the proper hash position
			if ( $_->[6] =~ /primary/ ) {
				$keywords{'01primary'}      = $kw_result_set_a->[0][0];
			} elsif ( $_->[6] =~ /t1-secondary/ ) {
				$keywords{'02t1-secondary'} = $kw_result_set_a->[0][0];
			} elsif ( $_->[6] =~ /t2-secondary/ ) {
				push @{$keywords{'03t2-secondary'}}, $kw_result_set_a->[0][0];
			}
		}
	}
	# <mlr 050504 - END: implement list_use table for keywords>
			
	#### get the keyword variations related to the page record that has this page_id primary key
	# form the sql
	$sql_stmt = qq(select kwv_keyword from kwv where kwv_page_id=$pid);
	# print debug info
	print "$sql_stmt\n"                                          if $opt_d =~ /sql/;
	# execute the query
	$result_set_a = $DBH->selectall_arrayref($sql_stmt)
		or die "keywords.pl::Cannot execute query: $DBI::err ($DBI::errstr)";
	# put the results into a lexical hash
	$keywords{'04keyword_vars'} = [];
	foreach (@$result_set_a) {
		push @{$keywords{'04keyword_vars'}}, $_->[0];
	}
	# print debug info
	print Data::Dumper->Dump([\%keywords], [qw(keywords_from_db)]) if $opt_d =~ /sql/; 
	
	#### return the keywords in a hash
	return %keywords;
} # end sub get_keywords_from_database

###############################################################################
## NAME:
##     get_page_details_from_database
## INPUT PARAMETERS:
##     $d                     = holds the value of $opt_d as passed in from the command line
##                              (e.g.: 'kwv-kwp-parsed-sql-args-cont')
##     $dbh                   = desc_param_here
##                              (e.g.: this is a reference to an object)
##     $pid                   = holds the primary key for the web site page
##                              (e.g.: 1)
##     $sid                   = holds the primary key for the web site
##                              (e.g.: 1)
##     %c                     = holds a copy of the hash for manipulating content
##                              (e.g.: copy of global %CONTENT)
## OUTPUT PARAMETERS:
##     none
## RETURN VALUES:
##     Success = returns a lexical hash to be assigned to global hash (%CONTENT) that
##               contains content information
##     Failure = n/a
## DESCRIPTION:
##     This sub retrieves web page content from the database. It also has some 
##     logic that searches for my self-defined content delimiters
##     <content>begin</content>
##     and
##     <content>end</content>.
##     That logic finds those delimiters and captures only the content between them.
##     I established a convention to insert these delimiters in EVERY web page
##     file so that I can distinguish between the full content of a web page
##     (that includes all the peripheral controls, includes, etc.) and the actual
##     value added content that I want to keyword optimize.
##     
###############################################################################
sub get_page_details_from_database {
	#### bring in subroutine arguments
	my ( $d, $dbh, $pid, $sid, %c ) = @_;                 # $d holds value of $opt_d as passed in from the command line
	                                                      # $dbh holds MySQL db connection object
	                                                      # $pid holds the primary key for the web site page 
	                                                      # $sid holds the primary key for the web site
	                                                      # %c holds a copy of the hash for manipulating content
	#### set up lexical scalars
	my $result_set_a     = "";                            # ref to an array containing arrays representing records returned
	my $sql_stmt         = "";                            # put an SQL statement to execute in this scalar
	my $site_local_store = "";                            # holds local store retrieve, e.g., C:/sites/car_audio
	my $page_content     = "";                            # holds content for the page
	my $page_folder      = "";                            # holds folder for the page, e.g., main-articles
	my $page_filename    = "";                            # holds filename for page, e.g., existing_car_audio.htm
	my $page_h1          = "";                            # holds sub-heading for page, e.g., <H1>heading</H1>
	my $page_h2          = "";                            # holds sub-heading for page, e.g., <H2>sub-heading</H2>
	
	#### set up lexical hashes
	my %content     = ();                                # put the keywords retrieved from the database here
	
	# print debug info
	print "-" x 80 . "\n"                                        if $opt_d =~ /sql/;
	print "From sub get_page_details_from_database\n"            if $opt_d =~ /sql/;

	#### get the local store location from the site record that has this site_id primary key
	# form the sql
	$sql_stmt = qq(select site_local_store from sites where site_id=$sid);
	# print debug info
	print "$sql_stmt\n"                                          if $opt_d =~ /sql/;
	# execute the query
	$result_set_a = $DBH->selectall_arrayref($sql_stmt)
		or die "keywords.pl::Cannot execute query: $DBI::err ($DBI::errstr)";
	# put the local store location temporarily into a lexical
	$site_local_store       = $result_set_a->[0][0];
	
	#### get the page's folder and file name from the page record that has this page_id primary key
	# form the sql
	$sql_stmt = qq(select page_content,page_folder,page_file_name,page_h1,page_h2 from pages where page_id=$pid);
	# print debug info
	print "$sql_stmt\n"                                          if $opt_d =~ /sql/;
	# execute the query
	$result_set_a = $DBH->selectall_arrayref($sql_stmt)
		or die "keywords.pl::Cannot execute query: $DBI::err ($DBI::errstr)";
	# put the local store location temporarily into a lexical
	$page_content           = [split /\n/, $result_set_a->[0][0]];
	$page_folder            = $result_set_a->[0][1];
	$page_filename          = $result_set_a->[0][2];
	$page_h1                = $result_set_a->[0][3];
	$page_h2                = $result_set_a->[0][4];
	
	# print debug info
	print Data::Dumper->Dump([\%c], [qw(c_default)])         if $opt_d =~ /sql/;

	#### build absolute pname to file we want to parse
	# and here are the absolute filenames
	foreach ( grep /name/, keys %c ) { 
		$c{$_} = $site_local_store . "/" . $page_folder . "/" . $page_filename;
	}
	# append "_analyzed" to the filename (I intend to output a syntax highlighted version of the analyzed content)
	($c{'file_name_analyzed'} = $c{'file_name_analyzed'}) =~ s/\.htm$/_analyzed\.htm/; # e.g. existing_car_audio_analyzed.htm
	# save the page contents retrieved from the database <mlr 050218>
	if ( $G{'whole_doc'} ) {
		$c{'file_contents_original'} = $page_content;
	} else {
		my $saw_start_tag = 0;
		my @f_c = ();
		
		foreach ( @$page_content ) {
			# <mlr 060721: I must watch for the HTML comment delimiters here so that I can get accurate word count>
			my $line = $_;
			# <mlr 060721: begin - eliminate default h1 and h2 lines from the content that I will analyze later>
			# the next two lines show my original default content for h1 and h2 in pages.page_content
			next if $line =~ m%<h1>H1</h1>%;
			next if $line =~ m%<h2>H2</h2>%;
			# I updated the default content for h1 and h2 in pages.page_content so that it works in
			# all cases: analysis from dB, analysis from file, Elance author template, 
			# web page export template.
			next if $line =~ m%<h1>PUT_HEADLINE_HERE</h1>%;
			next if $line =~ m%<h2>PUT_SUB_HEADLINE_HERE</h2>%;
			# <mlr 060721: end - eliminate default h1 and h2 lines from the content that I will analyze later>
			# <mlr 060725: see my example of a default pages.page_content value it the POD below in parse_single_words_from_content.>
			if ( m%<content>begin</content>% ) {
				$saw_start_tag = 1;
				push @f_c, ($line);
			} elsif ( m%<content>end</content>% ){
				$saw_start_tag = 0;
				push @f_c, ($line);
			} elsif ( $saw_start_tag ) {
				# <mlr 050524: BEGIN - add substitution for fields page_h1 and page_h2>
				if ( m%<!--</headline>-->% ) {
					push @f_c, ($line, $page_h1);
				} elsif ( m%<!--</sub-headline>-->% ) {
					push @f_c, ($line, $page_h2);
				} else {
					push @f_c, ($_);
				}
				# <mlr 050524: END - add substitution for fields page_h1 and page_h2>
			}
			next;
		}
		chomp @f_c;
		$c{'file_contents_original'} = \@f_c;
	}
	# print debug info
	print Data::Dumper->Dump([\%c], [qw(c_after_db_read)])         if $opt_d =~ /sql/;
	
	#### return the content info in a hash
	return %c;
} # end sub get_page_details_from_database

###############################################################################
## NAME:
##     parse_either_traditional_or_kwv_from_content
## INPUT PARAMETERS:
##     $d                     = holds the value of $opt_d as passed in from the command line
##                              (e.g.: 'kwv-kwp-parsed-sql-args-cont')
##     $kw                    = holds the keyword or keyword phrase we are parsing out
##                              (e.g.: 'car audio' or 'car')
##     $kwt                   = holds the keyword type
##                              (e.g.: 'car audio' or 'car')
## OUTPUT PARAMETERS:
##     none
## RETURN VALUES:
##     Success = %parsing_results (a hash containing the parsing results)
##     Failure = n/a
## DESCRIPTION:
##     I implemented this sub to take keywords AND/OR keyword phrases and parse
##     them out of the content I write for my affiliate marketing web sites. The
##     parsing here differs from the parsing in the sub parse_single_words_from_content.
##
##     Here I basically employ the substr perl function to find the kw or kwp.
##     I had to add a check of the next character after I found a kw or kwp mainly to 
##     distinguish between singular and plural forms of the words.
##
##     There, in parse_single_words_from_content I use code I found in the Perl
##     Cookbook. That code very efficiently parses every SINGLE word from the content.
##     However, it fails to find PHRASES. I use the results from this sub to 
##     perform a sanity check on my results obtained in this sub.
##     
###############################################################################
sub parse_either_traditional_or_kwv_from_content {

	#### bring in subroutine arguments
	my ( $d, $kw, $kwt ) = @_;                            # $d holds value of $opt_d as passed in from the command line
	                                                      # $kw holds the keyword we are parsing out
	#### set up lexical scalars
	my $num_found       = 0;                              # indicates how many keyword instances we found while parsing
	my $kw_length       = 0;                              # indicates the line number we are looking at
	my $line_num        = 0;                              # indicates the line number we are looking at
	my $pos             = -1;                             # indicates the column number we are looking at
	
	# <mlr 050222: begin - store results in a structure conducive to forming a PHP serialized string>
	#### set up lexical hashes
	my %parsing_results = ();                             # put parsing results here
	
	#### store, right away, what we have 
	$parsing_results{'001kw_type'} = $kwt;
	$parsing_results{'002keyword'} = $kw;
	$parsing_results{'003seen'}    = $SEEN{$kw};
	# <mlr 050222: end - store results in a structure conducive to forming a PHP serialized string>

	# determine the length of the keyword string
	$kw_length = length($kw);

	# set up lexical arrays
	my @content  = @{$CONTENT{'file_contents_original'}}; # make a copy of the file contents that we will use while parsing

	# print debug info
	print Data::Dumper->Dump([\@content], [qw(content)]) if $d =~ /lines/; # -d lines
	
	#### Parse out the keywords (or keyword variaitons) from the file we read in
	foreach my $line ( @content ) {
		# increment the line counter
		++$line_num;
		while ( ($pos = index(lc $line, lc $kw, $pos)) > -1 ) {

			# increment the column counter
			$pos++;

			# find the character following the keyword
			my $char = substr($line, $pos + $kw_length - 1, 1);
			
			# break out of this loop if alphanumerics follow the keyword
			next if $char =~ /\w/;
			
			# count this keyword instance
			$num_found++;

			# print debug info (this actually prints both traditional keyword results and new keyword variations results)
			printf ("%3d ", $num_found)                                                      if $d =~ /parsed/;
			print "Found -->$kw<-->$char<-- at line $line_num, column " . ($pos + 0) . ".\n" if $d =~ /parsed/;
			
			# <mlr 050222: begin - store results in a structure conducive to forming a PHP serialized string>
      my $key = sprintf("%03dfound", $num_found);
			$parsing_results{$key} = {'line'        => $line_num,
																'column'      => $pos,
																'next_letter' => $char
															};
						# <mlr 050222: end - store results in a structure conducive to forming a PHP serialized string>
		}
	}
	# print debug info
	print Data::Dumper->Dump([\%parsing_results], [qw(parsing_results)]) if $d =~ /single_pr/; # -d lines

	# return the hash that contains the parsed results
	return %parsing_results;
} # end sub parse_either_traditional_or_kwv_from_content

###############################################################################
## NAME:
##     parse_single_words_from_content
## INPUT PARAMETERS:
##     $d                     = holds the value of $opt_d as passed in from the command line
##                              (e.g.: 'kwv-kwp-parsed-sql-args-cont')
##     $contents              = holds an arrayref that contains the original html file (or DB field) contents
##                              (e.g.: 'It's satellite radio. Imagine yourself...')
## OUTPUT PARAMETERS:
##     %SEEN (this sub DIRECTLY CHANGES this global hash)
## RETURN VALUES:
##     Success = none
##     Failure = n/a
## DESCRIPTION:
##     In this sub I use code I found in the Perl Cookbook. 
##     That code very efficiently parses every SINGLE word from the content.
##     However, it fails to find PHRASES. I use the results from this sub to 
##     perform a sanity check on the results I obtain from my own implementation
##     in parse_either_traditional_or_kwv_from_content
##     
## <mlr 060721 - I need to omit my HTML comment delimiters from %SEEN. I'm getting 19 TOTAL WORDS
##               with my default content. That's 6.33% of 300 words, a significant error. I show
##               below the default value for the pages.page_content field.
=pod
<!--<content>begin</content>-->
<!--</headline>-->
<h1>PUT_HEADLINE_HERE</h1>
<p>PUT_PARAGRAPH_1_HERE</p>
<!--</sub-headline>-->
<h2>PUT_SUB_HEADLINE_HERE</h2>
<p>PUT_PARAGRAPH_2_HERE</p>
<!--<content>end</content>-->
=cut
###############################################################################
sub parse_single_words_from_content {
	#### bring in subroutine arguments
	my ( $d, $contents ) = @_;                            # $d holds value of $opt_d as passed in from the command line
                                                        # $contents holds an arrayref that contains the original html file (or DB field) contents
                                                        
	#### set up lexical scalars
	my $x = ();                                           # lexical scalar

	#### begin from Perl Cookbook p. 280 #################################################
	# <mlr 050120> This cookbook code works great for single words. It fails in our keyword
	#              search task because keywords are many times phrases like "CD player". So
	#              the result of this code is $SEEN{'CD'}-->1<-- and $SEEN{'player'}-->1<--
	#              rather than $SEEN{'CD player'}-->1<--. I will keep the code because I
	#              think it is useful.
	print "\$#\$contents-->$#$contents<--\n" if $d =~ /seen/; 
	foreach ( @$contents ) {
		my $line = $_;
		# print $line, "\n";
		# <mlr 060721: begin - omit HTML comment delimiters>
		next if $line =~ m%<!--<content>begin</content>-->%;
		next if $line =~ m%<!--<headline>-->%;               # <mlr 060725: unused in templates>
		next if $line =~ m%<!--</headline>-->%;
		next if $line =~ m%<!--<sub-headline>-->%;           # <mlr 060725: unused in templates>
		next if $line =~ m%<!--</sub-headline>-->%;
		next if $line =~ m%<!--<content>end</content>-->%;
		# <mlr 060721: begin - omit HTML comment delimiters>
		
		#while ( /(\w[\w'-]*)/g ) {
		while ( /([a-zA-Z0-9][a-zA-Z0-9']*)/g ) {
			$SEEN{lc $1}++;
		}
	}

	# print debug info
	print Data::Dumper->Dump([\%SEEN], [qw(words_seen_in_file)]) if $d =~ /seen/; # -d hash httpd
	#### end from Perl Cookbook p. 280   #################################################
	
} # end sub parse_single_words_from_content

###############################################################################
## NAME:
##     serialize_parsing_results_for_PHP
## INPUT PARAMETERS:
##     $d                     = holds the value of $opt_d as passed in from the command line
##                              (e.g.: 'kwv-kwp-parsed-sql-args-cont')
##     $pr                    = ref to array of hashes containing the parsing results
##                              see pod in below in this sub routine definition for an example
## OUTPUT PARAMETERS:
##     none
## RETURN VALUES:
##     Success = put_return_value_here
##     Failure = n/a
## DESCRIPTION:
##     The code in this sub transforms the parsed results produced by 
##     the subroutine parse_either_traditional_or_kwv_from_content
##     into a PHP serialized representation. I need that representation so that
##     I can unserialize it in my PHP code in the WPM web site. Then I can display
##     the parsed results in a my viewer of choice (the web browser).
##     
###############################################################################
sub serialize_parsing_results_for_PHP {
	#### bring in subroutine arguments
	my ( $d, $pr ) = @_;                                  # $d holds value of $opt_d as passed in from the command line
	                                                      # $pr ref to array of hashes containing the parsing results
	#### set up lexical scalars
	my $index                  = 0;                       # a generic counter to keep track of array indicies
	my $num_1st_level_elements = 0;                       # capture here a count of 1st level array elements
	my $serialize              = "";                      # I put the serialized information here
	
	#### set up lexical arrays
	my @stats                  = ();                      # put front end slice of @PR here so PHP array count remains accurate
	
	# slice off the parsing stats
	foreach ( 0..3) {
		@stats = (@stats, shift @$pr);
	}
  # print debug info (look at parsing results)
  print Data::Dumper->Dump([\@stats], [qw(stats)]) if $opt_d =~ /stats/; # -d hash httpd
	
	#### PHP serialization specifies EXACTLY how many elements an array contains,
	# so we get the number of 1st level arrays here (050426 add another +1 to account for stats)
	$num_1st_level_elements = $#{$pr} + 1 + 1;
	# serialize string - begin constructing by declaring an array with length equal to the number of 1st level array elements
	$serialize = "a:$num_1st_level_elements\:{";

	##### <mlr 050426 - BEGIN: add total words seen to PHP output so that I can calculate keyword density>
	# serialize string - continue constructing by declaring the index of this 1st level array element
	$serialize .= "i:$index;";
	$index++;
	
	# serialize string - now declare a 2nd level array with length equal to 4
	$serialize .= "a:4\:{";
	
	# serialize string - now add key value pair for "TOTAL_WORDS"
	$serialize .= "s:11:\"TOTAL_WORDS\";i:";
	# take the total words off the parsing results array
	$serialize .= shift @stats;
	$serialize .= ";";
	
	# serialize string - now add key value pair for "from_file"
	$serialize .= "s:9:\"from_file\";i:";
	# take the total words off the parsing results array
	$serialize .= shift @stats;
	$serialize .= ";";
	
	# serialize string - now add key value pair for "whole_doc"
	$serialize .= "s:9:\"whole_doc\";i:";
	# take the total words off the parsing results array
	$serialize .= shift @stats;
	$serialize .= ";";
	
	# serialize string - now add key value pair for "debug"
	$serialize .= "s:5:\"debug\";i:";
	# take the total words off the parsing results array
	$serialize .= shift @stats;
	$serialize .= ";";
	
	
	$serialize .= "}";
		
	##### <mlr 050426 - END: add total words seen to PHP output so that I can calculate keyword density>
	
	#### 1st level elements
	# this foreach loop cycles through the 1st level hashes - structures for primary, t1-secondary, t2-secondary, kw variaitons
	foreach my $l1 ( @$pr ) { # start f1
		# serialize string - continue constructing by declaring the index of this 1st level array element
		$serialize .= "i:$index;";
		$index++;
		
		# We need to know how many instances of the kw the parsing found
		# so that we can explicitly state the 2nd level array size
		# when we construct the serialized string. We lexically scope here
		# so that the variable resets every time through the loop.
		my $num_found = grep /found/, (sort keys %$l1);
		
		# We know we have 3 string values in the 2nd level hashes (001kw_type, 002keyword, 003seen)
		# so we add 3 to the $num_found to get the final number of elements in this 2nd level serialized array
		my $total_2nd_level_elements = $num_found + 3;
		
		# serialize string - now declare a 2nd level array with length equal to $total_2nd_level_elements
		$serialize .= "a:$total_2nd_level_elements\:{";
		
		#### 2nd level scalar elements
		# this foreach loop processes all the string values in the 1st level hashes with keys: 001kw_type, 002keyword, 003seen
		foreach my $l1_key ( grep !/found/, (sort keys %$l1) ) { # start f2
			# retrieve the value for this l1 key before we strip the hash key ordering characters
			my $l1_value = $l1->{$l1_key};
			# strip off the hash key ordering characters
			$l1_key =~ s/^\d\d\d//;
			# we need the length of each string so that we can explicitly state such in the serialization
			my $length = length $l1_key;
			# serialize string - now add a key for 2nd level array scalar elements
			$serialize .= "s:$length:\"$l1_key\";";
			# php strings and integers differ in their serialized format, so take care of this in an if statement
			if ( $l1_key =~ /seen/ ) {
   			# serialize string - now add values for 2nd level array scalar elements
  			$serialize .= "i:" . ($l1_value ? $l1_value : 0) . ";";
 			} else {
 				# we need the length of each string so that we can explicitly state such in the serialization
  			my $length = length $l1_value;
  			# serialize string - now add values for 2nd level array scalar elements
  			$serialize .= "s:$length:\"$l1_value\";";
			}				
		} # end f2
		
		#### 2nd level array elements
		# this foreach loop processes all the array values in the 1st level hashes with keys: 001found, 002found, ..., nnnfound
		foreach my $l1_key ( grep /found/, (sort keys %$l1) ) { # start f3
			# retrieve the value for this l1 key before we strip the hash key ordering characters
			my $l2_key = $l1->{$l1_key};
			# we need the length of each string so that we can explicitly state such in the serialization
			my $length = 8;
			# serialize string - now add a key for the 2nd level array scalar element
			$serialize .= "s:$length:\"$l1_key\";";
  		# serialize string - now add a declaration for the 3rd level "found" arrays
  		$serialize .= "a:3\:{";
			# serialize string - now add a key for the 3rd level element that indicates the line on which we found the kw
			$serialize .= "s:4:\"line\";";
 			# serialize string - and add a the value for the 3rd level element that indicates the line on which we found the kw
			$serialize .= "i:$l2_key->{'line'};";
 			# serialize string - now add a key for the 3rd level element that indicates the column in which we found the kw
			$serialize .= "s:6:\"column\";";
 			# serialize string - and add a the value for the 3rd level element that indicates the column in which we found the kw
			$serialize .= "i:$l2_key->{'column'};";
 			# serialize string - now add a key for the 3rd level element that indicates the character following the kw we found
			$serialize .= "s:11:\"next_letter\";";
 			# serialize string - and add a the value for the 3rd level element that indicates the character following the kw we found
			$serialize .= "s:1:\"$l2_key->{'next_letter'}\";"; 		
   		#### end constructing the 3rd level of the serialized string
   		$serialize .= "}";
		} # end f3
		#### end constructing the 2nd level of the serialized string
		$serialize .= "}";
	}	# end f1
	
	#### end constructing the 1st level of the serialized string
	$serialize .= "}";
	
	# print debug info (look at parsing results)
	print Data::Dumper->Dump([\$serialize], [qw(serialize)]) if $opt_d =~ /ser/;
	
	# return the serialized result
	return $serialize;
=pod  I used the PHP array further down to produce the following serialized representaion.
      <mlr 050426>
      note that I added "total words" and "BUI setting" to the serialized output so the actual
      results differ a bit from what I show below. I put an new example here:
      a:4:{i:0;a:4:{s:11:"TOTAL_WORDS";i:19;s:9:"from_file";i:1;s:9:"whole_doc";i:0;s:5:"debug";i:0;}i:1;a:3:{s:7:"kw_type";s:7:"primary";s:7:"keyword";s:3:"362";s:4:"seen";i:0;}i:2;a:3:{s:7:"kw_type";s:12:"t1-secondary";s:7:"keyword";s:3:"531";s:4:"seen";i:0;}i:3;a:3:{s:7:"kw_type";s:12:"t2-secondary";s:7:"keyword";s:3:"523";s:4:"seen";i:0;}}
      <mlr 050426>

      a:5:{i:0;a:5:{s:7:"kw_type";s:9:"01primary";s:7:"keyword";s:10:"cd players";s:4:"seen";i:2;s:8:"001found";a:3:{s:4:"line";i:2;s:6:"column";i:17;s:11:"next_letter";s:1:",";}s:8:"002found";a:3:{s:4:"line";i:3;s:6:"column";i:15;s:11:"next_letter";s:1:",";}}i:1;a:5:{s:7:"kw_type";s:14:"02t1-secondary";s:7:"keyword";s:10:"cd players";s:4:"seen";i:2;s:8:"001found";a:3:{s:4:"line";i:2;s:6:"column";i:17;s:11:"next_letter";s:1:",";}s:8:"002found";a:3:{s:4:"line";i:3;s:6:"column";i:15;s:11:"next_letter";s:1:",";}}i:3;a:5:{s:7:"kw_type";s:14:"03t2-secondary";s:7:"keyword";s:14:"cassette to cd";s:4:"seen";i:2;s:8:"001found";a:3:{s:4:"line";i:2;s:6:"column";i:17;s:11:"next_letter";s:1:",";}s:8:"002found";a:3:{s:4:"line";i:3;s:6:"column";i:15;s:11:"next_letter";s:1:",";}}i:4;a:5:{s:7:"kw_type";s:14:"04keyword_vars";s:7:"keyword";s:2:"cd";s:4:"seen";i:2;s:8:"001found";a:3:{s:4:"line";i:2;s:6:"column";i:17;s:11:"next_letter";s:1:",";}s:8:"002found";a:3:{s:4:"line";i:3;s:6:"column";i:15;s:11:"next_letter";s:1:",";}}s:14:"04keyword_vars";a:6:{i:0;s:2:"cd";i:1;s:6:"player";i:2;s:7:"players";i:3;s:7:"changer";i:4;s:8:"changers";i:5;s:7:"cassete";}}

			The following array is valid PHP.                            Below I show an example of the perl equivalent for on PHP top level array
      $my_array = array(                                           $a_hash = {                                      
      	0 => array('kw_type'     => 'primary',                                 '002found' => {                      
      						 'keyword'     => 'cd players',                                              'next_letter' => ',',
      						 'seen'        => 2,                                                         'column' => 43,      
      						 '001found'    => array('line'        => 2,                                  'line' => 6          
      																		'column'      => 17,                               },                     
      																		'next_letter' => ','),               '006found' => {                      
      						 '002found'    => array('line'        => 3,                                  'next_letter' => '<',
      																		'column'      => 15,                                 'column' => 110,     
      																		'next_letter' => ',')                                'line' => 102        
      							),                                                                       },                     
      	1 => array('kw_type'     => 't1-secondary',                            '008found' => {                      
      						 'keyword'     => 'cd players',                                              'next_letter' => '<',
      						 'seen'        => 2,                                                         'column' => 190,     
      						 '001found'    => array('line'        => 2,                                  'line' => 313        
      																		'column'      => 17,                               },                     
      																		'next_letter' => ','),               '003found' => {                      
      						 '002found'    => array('line'        => 3,                                  'next_letter' => ',',
      																		'column'      => 15,                                 'column' => 78,      
      																		'next_letter' => ',')                                'line' => 6          
      							),                                                                       },                     
      	3 => array('kw_type'     => 't2-secondary',                            '007found' => {                      
      						 'keyword'     => 'cassette to cd',                                          'next_letter' => '<',
      						 'seen'        => 2,                                                         'column' => 250,     
      		   		 	 '001found'    => array('line'        => 2,                                  'line' => 186        
      																		'column'      => 17,                               },                     
      																		'next_letter' => ','),               '004found' => {                      
      					   '002found'    => array('line'        => 3,                                  'next_letter' => ',',
      																		'column'      => 15,                                 'column' => 95,      
      																		'next_letter' => ',')                                'line' => 6          
      							),                                                                       },                     
      	4 => array('kw_type'     => 'keyword_vars',                            '001found' => {                      
      						 'keyword'     => 'cd',                                                      'next_letter' => ':',
      						 'seen'        => 2,                                                         'column' => 39,      
      						 '001found'    => array('line'        => 2,                                  'line' => 4          
      																		'column'      => 17,                               },                     
      																		'next_letter' => ','),               '003seen' => undef,                  
      						 '002found'    => array('line'        => 3,                  '001kw_type' => 'primary',           
      																		'column'      => 15,                 '002keyword' => 'cd players',        
      																		'next_letter' => ',')                '005found' => {                      
      							),                                                                         'next_letter' => ',',
      	'04keyword_vars' => array(                                                             'column' => 114,     
      													 'cd',                                                         'line' => 6          
      													 'player',                                                   }                      
      													 'players',                                  };                                     
      													 'changer',
      													 'changers',
      													 'cassete'
      												 )
      );
=cut				
} # end sub serialize_parsing_results_for_PHP

__END__

##### The bone yard starts here

###############################################################################
## NAME:
##     put_sub_name_here
## INPUT PARAMETERS:
##     $d                     = holds the value of $opt_d as passed in from the command line
##                              (e.g.: 'kwv-kwp-parsed-sql-args-cont')
##     $xxx                   = desc_param_here
##                              (e.g.: 'value')
## OUTPUT PARAMETERS:
##     none
## RETURN VALUES:
##     Success = put_return_value_here
##     Failure = n/a
## DESCRIPTION:
##     put_description_here
##     
###############################################################################

# <mlr 050215: cut begin>
# bring in the name of the file to analyze from the commandline,
# otherwise, use the default filename
if ( $ARGV[0] ) {
	($CONTENT{'file_name_original'}) = @ARGV[0];                              # e.g. car_cd_players.htm
	($CONTENT{'file_name_analyzed'}  = $ARGV[0]) =~ s/\.htm$/_analyzed\.htm/; # e.g. car_cd_players_analyzed.htm
}
# print debug info
if ( $opt_d =~ /stop/ ) {
	my $arg_message = "";
	$arg_message  = "Checking that we processed the command line args properly: \n";
	$arg_message .= "\$CONTENT{'file_name_original'}-->$CONTENT{'file_name_original'}<--\n";
	$arg_message .= "\$CONTENT{'file_name_analyzed'}-->$CONTENT{'file_name_analyzed'}<--\n";
	die $arg_message;
}

{
	#### build absolute pname to file we want to parse
	# need pwd to build an absolute pname
	my $pwd                  = cwd();
	# print debug info
	print "pwd-->$pwd<--\n" if $opt_d =~ /dir/; # -d dir
	
	# and here are the absolute filenames
	foreach ( grep /name/, keys %CONTENT ) { 
		my $filename = $CONTENT{$_};
		$CONTENT{$_} = $pwd . "/" . $filename;
	}
	# print debug info
	print Data::Dumper->Dump([\%CONTENT], [qw(CONTENT_before_read)]) if $opt_d =~ /hash/; # -d hash httpd
}
# <mlr 050215: cut end>

	##### Swap the contents of the files
	# get filename keys in original order
	my @fname_keys = grep /name/, keys %CONTENT;
	# get file content keys in original order
	my @fcont_keys = grep /contents/, keys %CONTENT;

	# we will swap file content by swapping names in our set up to write files
	open (OUT_0, ">$CONTENT{$fname_keys[1]}"); # OUT_0 -> file_name_analyzed
	open (OUT_1, ">$CONTENT{$fname_keys[0]}"); # OUT_1 -> file_name_original
	
	# actually write to file handles in original order to complete file swap
	print OUT_0 $CONTENT{$fcont_keys[0]}; # -d cont
	print OUT_1 $CONTENT{$fcont_keys[1]}; # -d cont
	