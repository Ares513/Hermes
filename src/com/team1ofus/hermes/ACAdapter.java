package com.team1ofus.hermes;

import completely.IndexAdapter;
import completely.data.ScoredObject;
import completely.text.index.FuzzyIndex;
import completely.text.index.PatriciaTrie;
import completely.text.match.EditDistanceAutomaton;

import java.util.Collection;

import com.team1ofus.hermes.Record;

/* AutoComplete Adapter. Totally just copied from the example on the completely github. 
 * Author Forrest Cinelli
 * */
public class ACAdapter implements IndexAdapter<Record>
{
    private FuzzyIndex<Record> index = new PatriciaTrie<>();

    @Override
    public Collection<ScoredObject<Record>> get(String token)
    {
        double threshold = Math.log(token.length() - 1);
        return index.getAny(new EditDistanceAutomaton(token, threshold));
    }

    @Override
    public boolean put(String token, Record value)
    {
        return index.put(token, value);
    }
}