/**
 * EventNotificationListener.java
 * Created on 10.03.2003, 20:43:16 Alex
 * Package: net.sf.memoranda
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package main.java.memoranda;

/**
 * The interface Event notification listener.
 */
/*$Id: EventNotificationListener.java,v 1.2 2004/01/30 12:17:41 alexeya Exp $*/
public interface EventNotificationListener {

    /**
     * Event is occured.
     *
     * @param ev the ev
     */
    void eventIsOccured(Event ev);

    /**
     * Events changed.
     */
    void eventsChanged();

}
