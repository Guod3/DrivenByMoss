// Written by Jürgen Moßgraber - mossgrabers.de
// (c) 2017-2025
// Licensed under LGPLv3 - http://www.gnu.org/licenses/lgpl-3.0.txt

package de.mossgrabers.controller.ableton.push.command.trigger;

import de.mossgrabers.controller.ableton.push.PushConfiguration;
import de.mossgrabers.controller.ableton.push.controller.PushControlSurface;
import de.mossgrabers.framework.command.trigger.Direction;
import de.mossgrabers.framework.command.trigger.mode.CursorCommand;
import de.mossgrabers.framework.daw.IModel;
import de.mossgrabers.framework.daw.data.bank.ISceneBank;
import de.mossgrabers.framework.featuregroup.IMode;
import de.mossgrabers.framework.view.Views;


/**
 * Command for cursor arrow keys.
 *
 * @author Jürgen Moßgraber
 */
public class PushCursorCommand extends CursorCommand<PushControlSurface, PushConfiguration>
{
    private final ISceneBank        sceneBank64;
    private final PushConfiguration configuration;


    /**
     * Constructor.
     *
     * @param direction The direction of the pushed cursor arrow
     * @param model The model
     * @param surface The surface
     */
    public PushCursorCommand (final Direction direction, final IModel model, final PushControlSurface surface)
    {
        super (direction, model, surface, false);

        this.configuration = this.surface.getConfiguration ();
        this.sceneBank64 = model.getSceneBank (64);
    }


    /**
     * Scroll scenes up.
     */
    @Override
    protected void scrollUp ()
    {
        final ISceneBank sceneBank = this.getSceneBank ();
        switch (this.surface.isShiftPressed () || this.isScenePlay () ? this.configuration.getCursorKeysSceneShiftedOption () : this.configuration.getCursorKeysSceneOption ())
        {
            case PushConfiguration.CURSOR_KEYS_SCENE_OPTION_MOVE_BANK_BY_PAGE:
                sceneBank.selectPreviousPage ();
                break;
            case PushConfiguration.CURSOR_KEYS_TRACK_OPTION_MOVE_BANK_BY_1:
                sceneBank.scrollBackwards ();
                break;
        }
    }


    /**
     * Scroll scenes down.
     */
    @Override
    protected void scrollDown ()
    {
        final ISceneBank sceneBank = this.getSceneBank ();
        switch (this.surface.isShiftPressed () || this.isScenePlay () ? this.configuration.getCursorKeysSceneShiftedOption () : this.configuration.getCursorKeysSceneOption ())
        {
            case PushConfiguration.CURSOR_KEYS_SCENE_OPTION_MOVE_BANK_BY_PAGE:
                sceneBank.selectNextPage ();
                break;
            case PushConfiguration.CURSOR_KEYS_TRACK_OPTION_MOVE_BANK_BY_1:
                sceneBank.scrollForwards ();
                break;
        }
    }


    /** {@inheritDoc} */
    @Override
    protected void scrollLeft ()
    {
        final IMode activeMode = this.surface.getModeManager ().getActive ();
        if (activeMode != null)
            activeMode.selectPreviousItemPage ();
    }


    /** {@inheritDoc} */
    @Override
    protected void scrollRight ()
    {
        final IMode activeMode = this.surface.getModeManager ().getActive ();
        if (activeMode != null)
            activeMode.selectNextItemPage ();
    }


    /** {@inheritDoc} */
    @Override
    protected ISceneBank getSceneBank ()
    {
        if (this.isScenePlay ())
            return this.sceneBank64;
        return this.model.getCurrentTrackBank ().getSceneBank ();
    }


    /** {@inheritDoc} */
    @Override
    protected void updateArrowStates ()
    {
        final ISceneBank sceneBank = this.getSceneBank ();
        final IMode mode = this.surface.getModeManager ().getActive ();
        final boolean shiftPressed = this.surface.isShiftPressed ();

        switch (shiftPressed || this.isScenePlay () ? this.configuration.getCursorKeysSceneShiftedOption () : this.configuration.getCursorKeysSceneOption ())
        {
            case PushConfiguration.CURSOR_KEYS_SCENE_OPTION_MOVE_BANK_BY_PAGE:
                this.scrollStates.setCanScrollUp (sceneBank.canScrollPageBackwards ());
                this.scrollStates.setCanScrollDown (sceneBank.canScrollPageForwards ());
                break;
            case PushConfiguration.CURSOR_KEYS_TRACK_OPTION_MOVE_BANK_BY_1:
                this.scrollStates.setCanScrollUp (sceneBank.canScrollBackwards ());
                this.scrollStates.setCanScrollDown (sceneBank.canScrollForwards ());
                break;
        }

        this.scrollStates.setCanScrollLeft (mode != null && (shiftPressed ? mode.hasPreviousItem () : mode.hasPreviousItemPage ()));
        this.scrollStates.setCanScrollRight (mode != null && (shiftPressed ? mode.hasNextItem () : mode.hasNextItemPage ()));
    }


    private boolean isScenePlay ()
    {
        return this.surface.getViewManager ().isActive (Views.SCENE_PLAY);
    }
}
