#!C:/Perl/bin/perl.exe
#!/usr/bin/perl -w
# openfirectl.pl
#
#  Discussion:
#
#    Will use Sysinternals pslist.exe to see if a process named like 
#    "openfire" is running. If not, it starts it. Otherwise, it does nothing.
#
#    This is a bare bones implementation simply to achieve on button testing.
#    Openfire comes with a *nix version of openfirectl. Maybe later I'll
#    convert it to Perl. Right now, I just want to continue with GOOS.
#
#  Modified:
#
#    17 Nov 2013
#
#  Author:
#
#    Mike Rocha
#
#  Reference:
#
#    Mike Rocha
use strict;
use vars( qw/$opt_d $opt_h $opt_v/ );
use Data::Dumper;
use Getopt::Std;
use Sys::Hostname;

################################################################################
#                                                                              #
#                           Global Variables                                   #
#                                                                              #
################################################################################

#### Scalars
# To make this more consistent, I might change implementation to use ENV in windows.
# e.g. SYSINTERNALS_HOME=C:\Users\Mike\Downloads\SysinternalsSuite
my $SIH = 'src\test\scripts\SysinternalsSuite_131101';

# point to openfire depending on the name of the host on which we're running 
# avoids install location problems between work ("vi-1057") and home ("roco-3") laptops
# The main problem is the hostname Openfire uses differs from one to other laptop.
my $OFH;


# create some Sysinternals commands
my $PSLIST_CMD = "$SIH\\pslist.exe openfire 2>&1";
my $PSKILL_CMD = "$SIH\\pskill.exe 2> nul";

# create some globals in which to catch Sysinternals command results
my $PSLIST_RESULT;

my $USAGE = "

usage: $0 -h | [-d <debug_flag>] <field_name=field_value> [<field_name1=field_value2> ... <field_nameN=field_valueN]

       -d (optional) turns on various debug output
       -h (optional) show this help information
       -v (optional) turns on verbose mode that automatically provides messages of actions occurring
       
       Examples:
         To get this help page:
           ./openfirectl.pl -h

         To make a connection to a database \"test\" and a host \"127.0.0.1\"
           ./openfirectl.pl -e xxxx db=test host=127.0.0.1
           
         Valid field=value pairs
           command    = <for_openfire_control>       e.g. defaults to 'start'
                                                          'start' means invoke openfire.exe unless it's already running
                                                          'kill' means kill openfire app and daemon (must explicity kill)
          
";

#### Hashes
# a hash for general purpose use
my %G        = ();

###############################################################################
#                                                                              #
#                              Main Logic                                      #
#                                                                              #
################################################################################
getopts('d:hv');

# Show usage if requested by a command line option
die $USAGE if $opt_h;

# set default command
$G{'command'} = 'start';

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

# set the Openfire server executable location base on the host
if (hostname eq "roco-3") {
	$OFH = 'lib\openfire_3_8_2\bin';
} else {
	$OFH = 'c:\dev\openfire_3_8_2\bin';
}
# check if openfire is running
$PSLIST_RESULT = `$PSLIST_CMD`;
if ($PSLIST_RESULT ne "" && $PSLIST_RESULT =~ /process openfire was not found/) {
	die "Use command=kill only when Openfire is already running." if ($opt_v && $G{'command'} eq 'kill');
	my $sleep_duration = 5;
	print "Starting Openfire ... this will take about $sleep_duration seconds ...\n" if $opt_v; ## print if verbose flag set
	system("start $OFH\\openfire.exe");
	sleep $sleep_duration;
} else {
	print "Openfire is already running\n" if $opt_v ; ## print if verbose flag set
	my @pids = parsePIDs($PSLIST_RESULT);
	# print debug info
	print Data::Dumper->Dump( [$PSLIST_RESULT], [qw(PSLIST_RESULT)] ) if $opt_d =~ /kill/;
	print Data::Dumper->Dump( [\@pids], [qw(pids)] ) if $opt_d =~ /kill/;
	foreach (@pids) {
	 system("$PSKILL_CMD $_") if $G{'command'} eq 'kill';
	}
	print "Openfire processes killed." if ($opt_v && $G{'command'} eq 'kill');
}

################################################################################
#                                                                              #
#                           Subroutine Definitions                             #
#                                                                              #
################################################################################

sub parsePIDs {
	#### bring in subroutine arguments
	my ( $pslist_result_p ) = @_;        # holds the process list results we want to parse for PIDs
	
	my @pslist;
	@pslist = map{ $_ if $_ =~ /openfire/ } split /\n/, $pslist_result_p; # keep only lines containing "openfire"
	@pslist = map{ (split /\s+/)[1] } @pslist; # keep only PID values residing in the 2nd field of each record
	return @pslist;
}
	