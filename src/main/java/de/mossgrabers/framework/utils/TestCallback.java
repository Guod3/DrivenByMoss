// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2025
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.framework.utils;

/**
 * Interface for signaling the start and end of the tests.
 *
 * @author Jürgen Moßgraber
 */
public interface TestCallback
{
    /**
     * Called before the testing starts.
     */
    void startTesting ();


    /**
     * Called after the testing ended.
     */
    void endTesting ();
}
