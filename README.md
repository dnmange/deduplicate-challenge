# deduplicate-challenge

## Challenge
Take a variable number of identically structured json records and de-duplicate
the set.

An example file of records is given in the accompanying &#39;leads.json&#39;. Output
should be same format, with dups reconciled
according to the following rules:

1. The data from the newest date should be preferred
2. duplicate IDs count as dups. Duplicate emails count as dups. Duplicate values
   elsewhere do not count as dups.
3. If the dates are identical the data from the record provided last in the list
   should be preferred

Simplifying assumption: the program can do everything in memory (don&#39;t worry
about large files)

The application should also provide a log of changes including some
representation of the source record, the output record and the individual field
changes
(value from and value to) for each field.

Please implement as a command-line java program.

## Build
1. git clone https://github.com/dnmange/deduplicate-challenge.git
2. run ./gradlew clean build shadowJar on deduplicate-function.

## Run Jar
1. Goto `cd build/libs`
2. Make sure to run the fat jar which is suffixed with "*-all.jar".  
   Command to Run 
   
   `java -jar deduplicate-function-1.0-SNAPSHOT-all.jar -i <input_filename> -o <output_filename>`.
   
### Example 
```
   java -jar deduplicate-function-1.0-SNAPSHOT-all.jar -i /Users/dmange/repos/deduplicate-function/sample_inputs/code_challenge_leads.json -o /Users/dmange/repos/deduplicate-function/sample_outputs/code_challenge_leads_output.json
```
3. Log changes will be printed in console and json file which is provided as argument will be created with deduplicate records.
   
   
## Samples
1. find input samples under samples_input/ directory
2. find output generated samples under samples_output/ directory
3. Sample log change will look something like below in console when you run the jar
   ```
      {"@timestamp":"2021-09-03T15:06:29.171-07:00","@version":1,"message":"For source id : jkj238238jdsnfsj23 and email : foo@bar.com , property _id changed from : \"jkj238238jdsnfsj23\" to : \"sel045238jdsnfsj23\" ","logger_name":"com.adobe.coding.challenge.deduplicate.ConsoleLeadLoggerImpl","thread_name":"main","level":"INFO","level_value":20000}
      {"@timestamp":"2021-09-03T15:06:29.191-07:00","@version":1,"message":"For source id : jkj238238jdsnfsj23 and email : foo@bar.com , property entryDate changed from : \"2014-05-07T17:30:20+00:00\" to : \"2014-05-07T17:32:20+00:00\" ","logger_name":"com.adobe.coding.challenge.deduplicate.ConsoleLeadLoggerImpl","thread_name":"main","level":"INFO","level_value":20000}
   ```

