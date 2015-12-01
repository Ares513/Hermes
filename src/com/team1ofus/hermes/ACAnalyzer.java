package com.team1ofus.hermes;

import completely.text.analyze.Analyzer;
import completely.text.analyze.tokenize.WordTokenizer;
import completely.text.analyze.transform.LowerCaseTransformer;

import java.util.Collection;

/* AutoComplete Analyzer. Totally just copied from the example on the completely github. 
 * Author Forrest Cinelli
 * */
public class ACAnalyzer extends Analyzer{
	private Analyzer tokenizer = new WordTokenizer();
    private Analyzer transformer = new LowerCaseTransformer();

    @Override
    public Collection<String> apply(Collection<String> input)
    {
        return tokenizer.apply(transformer.apply(input));
    }
}
