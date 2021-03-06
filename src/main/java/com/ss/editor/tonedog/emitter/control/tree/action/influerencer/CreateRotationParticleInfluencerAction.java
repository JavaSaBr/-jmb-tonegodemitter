package com.ss.editor.tonedog.emitter.control.tree.action.influerencer;

import com.ss.editor.annotation.FxThread;
import com.ss.editor.model.undo.editor.ModelChangeConsumer;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import org.jetbrains.annotations.NotNull;
import tonegod.emitter.Messages;
import tonegod.emitter.ParticleEmitterNode;
import tonegod.emitter.influencers.ParticleInfluencer;
import tonegod.emitter.influencers.impl.RotationInfluencer;

/**
 * The action to create a {@link RotationInfluencer} for a {@link ParticleEmitterNode}.
 *
 * @author JavaSaBr
 */
public class CreateRotationParticleInfluencerAction extends AbstractCreateParticleInfluencerAction {

    public CreateRotationParticleInfluencerAction(
            @NotNull NodeTree<ModelChangeConsumer> nodeTree,
            @NotNull TreeNode<?> node
    ) {
        super(nodeTree, node);
    }

    @Override
    @FxThread
    protected @NotNull String getName() {
        return Messages.PARTICLE_INFLUENCER_ROTATION;
    }

    @Override
    @FxThread
    protected @NotNull ParticleInfluencer createInfluencer() {
        return new RotationInfluencer();
    }
}
