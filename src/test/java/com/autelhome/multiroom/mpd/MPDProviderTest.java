package com.autelhome.multiroom.mpd;

import com.autelhome.multiroom.app.ApplicationConfiguration;
import org.junit.Test;

public class MPDProviderTest {

    final ApplicationConfiguration configuration = new ApplicationConfiguration("test");

    final MPDProvider testSubject = new MPDProvider(configuration);

    @Test(expected = MPDException.class)
    public void getMPDWithUnknownInstance() throws Exception {
        testSubject.getMPD(234);
    }

}