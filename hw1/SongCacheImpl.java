package week3.hw1;

import java.util.*;

public class SongCacheImpl implements SongCache{
    private Map<String, Integer> songs;
    private Map<String,Integer> sortedMap;


    public SongCacheImpl(){
        songs=new HashMap<>();
        sortedMap=new TreeMap<>((Comparator) new ValueComparator(songs));
    }
    @Override

   synchronized public void recordSongPlays(String songId, int numPlays) {
        if(songs.containsKey(songId)){
            songs.put(songId,songs.get(songId)+numPlays);
        }else{
            songs.put(songId,numPlays);
        }
    }

    @Override
    synchronized public int getPlaysForSong(String songId) {
        return songs.getOrDefault(songId,-1);
    }

    @Override
    synchronized public List<String> getTopNSongsPlayed(int n) {
        sortedMap.putAll(songs);
        List<String>topNSongs=new ArrayList<>();
        Iterator<Map.Entry<String,Integer>>itr=sortedMap.entrySet().iterator();
        while(itr.hasNext()&&n!=0){
            Map.Entry<String,Integer> entry=itr.next();
            topNSongs.add(entry.getKey());
            n--;
        }
        return topNSongs;
    }
}
