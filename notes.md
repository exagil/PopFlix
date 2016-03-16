Washbasin
=========

1. Verification that all tables are created
   Location: net/chiragaggarwal/android/popflix/repository/DatabaseHelperTest.java:30
   Problem Snippet A

2. Verification that all columns are present
   Location: net/chiragaggarwal/android/popflix/repository/DatabaseHelperTest.java:45
   Problem Snippet A


Problem Snippet A
  Problem:
          A code smell - while loop is being used
          and a test which contains loops is bad

     Why in washbasin when the problem is known:
          There is no way to build a cursor which I can
          compare with the cursor which I get from the database
          Hence I need to iterate through the cursor to verify whether it
          has the right values

ToDo:
  - Add default of isFavorite value to false
