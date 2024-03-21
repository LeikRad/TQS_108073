For this task, I decided to use my previous IES work for the static code Analysis, to evaluate the code quality of the project. A custom quality gate was created based on the default quality gate, with the addition of the following rules:
- Code Smells is greater than 0

This was done purely to more easily break the quality gate conditions, after which I added some code smells and issues to the code to test the quality gate. The code did not pass on the second evaluation, as expected.