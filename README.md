# spellchecker
Implementation and testing of different spellchecking algorithms.

## Tested Algorithms

### Naive Edit Distance
Naive spellchecking algorithm that computes the distance between two words with product of edit distance and inverse frequency value. Really slow since the word is checked against EVERY word in the dictionary.

### Peter Norvig's Algorithm
A smarter use of edit distance for spellchecking. The explanation of the algorithm and more detailed info can be found on Peter Norvig's website [here](http://norvig.com/spell-correct.html).

### SymSpell Algorithm
A much faster algorithm for spellchecking, that is still based on edit distance. The explanation of the algorithm with some test results can be found on [this blog post](https://medium.com/@wolfgarbe/1000x-faster-spelling-correction-algorithm-2012-8701fcd87a5f).

## Resources
* The text file called `big.txt` to create the dictionary of words for the spellcheckers is from Peter Norvig's website [here](http://norvig.com/spell-correct.html).
* The first test set is again from Peter Norvig's website [here](http://norvig.com/spell-correct.html).
* The second test set is from [here](http://aspell.net/test/).
