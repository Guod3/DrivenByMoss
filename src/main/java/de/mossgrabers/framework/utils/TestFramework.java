// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2019-2020
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.framework.utils;

import de.mossgrabers.framework.daw.IHost;

import java.util.LinkedList;


/**
 * Testframework.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class TestFramework
{
    private static final int           ANSWER_DELAY = 100;

    private IHost                      host;
    private final LinkedList<Runnable> scheduler    = new LinkedList<> ();


    /**
     * Constructor.
     *
     * @param host The controller host
     */
    public TestFramework (final IHost host)
    {
        this.host = host;
    }


    /**
     * Execute all scheduled test functions.
     */
    public void executeScheduler ()
    {
        if (this.scheduler.isEmpty ())
        {
            this.host.println ("Tests finished.");
            return;
        }

        int delay = ANSWER_DELAY;

        final Runnable exec = this.scheduler.remove ();
        try
        {
            exec.run ();
        }
        catch (final RuntimeException ex)
        {
            this.host.error (ex.getLocalizedMessage (), ex);
        }

        this.host.scheduleTask (this::executeScheduler, delay);
    }


    /**
     * Schedule a function for later test processing.
     *
     * @param f The function to schedule
     */
    public void scheduleFunction (final Runnable f)
    {
        this.scheduler.add (f);
    }
}
