The (newly written) compile_and_run script compiled them, ran them, and collected the output.

Also requires a slightly modified report.sh to report on whether or not sorting seems
to have produed correct results.

This one requires so much manual grading that you probably want to start with a csv 
spreadsheet (created either from the roster or the autos) and do most of the grading 
on that.  

Manual grading rubric:
	correct MergeSort.merge() output
		2pts	all correct
		1pt	often correct
		0pt	no/few correct large sorts

	correct MergeSort.merge() implementation
		1pt	did they actually implement the specified algorithm

	runs with plausible results
		0.5pt	reasonable ranges, variations, out-liers
		0.5pt	high counts O(N) or worse, quicksort lower cost

	analysis: multiple runs
		0.5pt	identify/discard outlying or meaningless results
			(e.g. because of warm-up, too few to measure,
			      garbage collection)

	analysis: relative performance
		0.5pt	compared the data with O(N) or O(N^2)
		0.5pt	compared the data with NlogN
		0.5pt	reasonable justification (not just "the book says")

	comments
		1pt	merge (the four cases)
		1pt	reasonable content, no TODO's

	style and formatting
		1pt	indentation and white space
