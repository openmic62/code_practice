package junitparams;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.*;

import junitparams.mappers.*;
import junitparams.usage.person_example.*;
import junitparams.usage.person_example.PersonTest.*;

@RunWith(JUnitParamsRunner.class)
public class FileParamsTest {

    @Test
    @FileParameters("src/test/resources/test.csv")
    public void loadParamsFromFileWithIdentityMapper(int age, String name) {
        assertTrue(age > 0);
    }

    @Test
    @FileParameters(value = "src/test/resources/test.csv", mapper = PersonMapper.class)
    public void loadParamsFromFileWithCustomMapper(Person person) {
        assertTrue(person.getAge() > 0);
    }

    @Test
    @FileParameters("classpath:test.csv")
    public void loadParamsFromFileAtClasspath(int age, String name) {
        assertTrue(age > 0);
    }

    @Test
    @FileParameters("file:src/test/resources/test.csv")
    public void loadParamsFromFileAtFilesystem(int age, String name) {
        assertTrue(age > 0);
    }

    @Test
    @FileParameters(value = "classpath:with_header.csv", mapper = CsvWithHeaderMapper.class)
    public void csvWithHeader(int id, String name) {
        assertTrue(id > 0);
    }

}
