package week3.hw1_3;

import static org.hamcrest.MatcherAssert.assertThat;
import  static org.hamcrest.*;
public class Test {
    public void BasicTest() {
        SongCache cache = new SongCacheImpl();
        cache.recordSongPlays("ID-1", 1);
        cache.recordSongPlays("ID-2", 2);
        cache.recordSongPlays("ID-3", 3);

        assertThat(cache.getPlaysForSong("ID-1"), is(1));
        assertThat(cache.getPlaysForSong("ID-2"), is(2));
        assertThat(cache.getPlaysForSong("ID-3"), is(3));
        assertThat(cache.getPlaysForSong("ID-4"), is(-1));

        assertThat(cache.getTopNSongsPlayed(2).contains("ID-1"), is(false));
        assertThat(cache.getTopNSongsPlayed(2).contains("ID-2"), is(true));
        assertThat(cache.getTopNSongsPlayed(2).contains("ID-3"), is(true));
        assertThat(cache.getTopNSongsPlayed(0).size(), is(0));
    }

}
