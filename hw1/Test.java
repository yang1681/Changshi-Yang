package week3.hw1;

import java.util.List;

import static java.util.Optional.empty;

import static org.hamcrest.MatcherAssert.assertThat;
import static sun.security.util.AnchorCertificates.contains;

public class Test {

    public void cacheIsWorking(){
        SongCache cache= new SongCache() {
            @Override
            public void recordSongPlays(String songId, int numPlays) {

            }

            @Override
            public int getPlaysForSong(String songId) {
                return 0;
            }

            @Override
            public List<String> getTopNSongsPlayed(int n) {
                return null;
            }
        };
        cache.recordSongPlays("ID-1",3);
        cache.recordSongPlays("ID-1",3);
        cache.recordSongPlays("ID-2",2);
        cache.recordSongPlays("ID-3",5);

        assertThat(cache.getPlaysForSong("ID-1"),is(4));
        assertThat(cache.getPlaysForSong("ID-9"),is(-1));
        assertThat(cache.getTopNSongsPlayed(2),contains("ID-3","ID-1"));
        assertThat(cache.getTopNSongsPlayed(0),is(empty()));
    }



}

