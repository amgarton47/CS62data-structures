Doing these with the normal auto_grader (which insists on knowing the names of all tests)
seemed impractical, due to the fact that they had written their own test modules and test cases.

So I went through and did a manual compile of everything (I could probably tease the compile_and_run script into doing this part if it knew about dependenceis), and then ran my runnem.sh script.

I think this could be induced to (mostly) auto-grade with a little more work.
