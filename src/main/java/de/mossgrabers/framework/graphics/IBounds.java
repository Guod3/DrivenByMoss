// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2025
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.framework.graphics;

/**
 * Boundaries to draw in.
 *
 * @author Jürgen Moßgraber
 */
public interface IBounds
{
    /**
     * The left bound.
     *
     * @return The value
     */
    double left ();


    /**
     * The top bound.
     *
     * @return The value
     */
    double top ();


    /**
     * The width bound.
     *
     * @return The value
     */
    double width ();


    /**
     * The height bound.
     *
     * @return The value
     */
    double height ();
}
