// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2020
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.controller.apc.command.continuous;

import de.mossgrabers.controller.apc.APCConfiguration;
import de.mossgrabers.controller.apc.controller.APCControlSurface;
import de.mossgrabers.framework.command.continuous.TempoCommand;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.utils.Timeout;
import de.mossgrabers.framework.view.ViewManager;
import de.mossgrabers.framework.view.Views;


/**
 * Additionally, display BPM on the grid.
 *
 * @author J&uuml;rgen Mo&szlig;graber
 */
public class APCTempoCommand extends TempoCommand<APCControlSurface, APCConfiguration>
{
    private final Timeout timeout;


    /**
     * Constructor.
     *
     * @param model The model
     * @param surface The surface
     * @param timeout The timeout object
     */
    public APCTempoCommand (final IModel model, final APCControlSurface surface, final Timeout timeout)
    {
        super (model, surface);

        this.timeout = timeout;
    }


    /** {@inheritDoc} */
    @Override
    public void execute (final int value)
    {
        super.execute (value);

        final ViewManager viewManager = this.surface.getViewManager ();
        if (!viewManager.isActiveView (Views.TEMPO))
            viewManager.setActiveView (Views.TEMPO);
        this.timeout.delay (viewManager::restoreView);
    }
}
