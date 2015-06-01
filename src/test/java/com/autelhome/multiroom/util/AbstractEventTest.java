package com.autelhome.multiroom.util;

import org.junit.Test;

import java.util.UUID;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.junit.Assert.assertNotEquals;

public class AbstractEventTest {

    private static class MyEvent extends AbstractEvent {

        protected MyEvent(final UUID aggregateId) {
            super(aggregateId);
        }
    }


    @Test
    public void shouldBeEqual() {
        final UUID aggregateId1 = UUID.randomUUID();
        final AbstractEvent event1 = new MyEvent(aggregateId1);
        final int version1 = 234;
        event1.setVersion(version1);

        final AbstractEvent event2 = new MyEvent(aggregateId1);
        event2.setVersion(version1);
        
        assertThat(event1).isEqualTo(event1);
        assertThat(event1).isEqualTo(event2);
    }

    @Test
    public void shouldNotBeEqual() {
        final UUID aggregateId1 = UUID.randomUUID();
        final AbstractEvent event1 = new MyEvent(aggregateId1);
        final int version1 = 234;
        event1.setVersion(version1);

        final AbstractEvent event2 = new MyEvent(UUID.randomUUID());
        event2.setVersion(version1);

        final AbstractEvent event3 = new MyEvent(aggregateId1);
        event3.setVersion(767);

        assertNotEquals(event1, " ");
        assertNotEquals(event1, null);
        assertThat(event1).isNotEqualTo(event2);
        assertThat(event1).isNotEqualTo(event3);
    }

    @Test
    public void hashCodeShouldBeTheSame() {
        final UUID aggregateId1 = UUID.randomUUID();
        final AbstractEvent event1 = new MyEvent(aggregateId1);
        final int version1 = 234;
        event1.setVersion(version1);
        final int hashCode1 = event1.hashCode();

        final AbstractEvent event2 = new MyEvent(aggregateId1);
        event2.setVersion(version1);
        final int hashCode2 = event2.hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCodeShouldNotBeTheSame() {
        final UUID aggregateId1 = UUID.randomUUID();
        final AbstractEvent event1 = new MyEvent(aggregateId1);
        final int version1 = 234;
        event1.setVersion(version1);
        final int hashCode1 = event1.hashCode();

        final AbstractEvent event2 = new MyEvent(UUID.randomUUID());
        event2.setVersion(version1);
        final int hashCode2 = event2.hashCode();

        final AbstractEvent event3 = new MyEvent(aggregateId1);
        event3.setVersion(767);
        final int hashCode3 = event3.hashCode();

        assertThat(hashCode1).isNotEqualTo(hashCode2);
        assertThat(hashCode1).isNotEqualTo(hashCode3);
    }
    
    @Test
    public void toStringShouldContainFields() throws Exception {
        final UUID aggregateId = UUID.randomUUID();
        final MyEvent event = new MyEvent(aggregateId);
        final int version = 345;
        event.setVersion(version);
        final String actual = event.toString();

        assertThat(actual).contains(aggregateId.toString());
        assertThat(actual).contains(Integer.toString(version));
    }

}