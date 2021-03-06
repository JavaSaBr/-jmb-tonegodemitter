package com.ss.editor.tonedog.emitter.control.tree.action;

import static com.ss.editor.util.EditorUtil.getDefaultLayer;
import static com.ss.rlib.common.util.ObjectUtils.notNull;
import com.jme3.scene.Node;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.extension.scene.SceneLayer;
import com.ss.editor.model.undo.editor.ModelChangeConsumer;
import com.ss.editor.model.undo.impl.AddChildOperation;
import com.ss.editor.tonedog.emitter.PluginMessages;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.action.AbstractNodeAction;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.editor.util.EditorUtil;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tonegod.emitter.ParticleEmitterNode;
import tonegod.emitter.influencers.impl.AlphaInfluencer;
import tonegod.emitter.influencers.impl.ColorInfluencer;
import tonegod.emitter.influencers.impl.SizeInfluencer;

/**
 * The action for creating new {@link ParticleEmitterNode}.
 *
 * @author JavaSaBr
 */
public class CreateParticleEmitterAction extends AbstractNodeAction<ModelChangeConsumer> {

    public CreateParticleEmitterAction(@NotNull NodeTree<?> nodeTree, @NotNull TreeNode<?> node) {
        super(nodeTree, node);
    }

    @Override
    @FxThread
    protected @Nullable Image getIcon() {
        return Icons.EMITTER_16;
    }

    @Override
    @FxThread
    protected @NotNull String getName() {
        return PluginMessages.MODEL_NODE_TREE_ACTION_CREATE_PARTICLE_EMITTER;
    }

    @Override
    @FxThread
    protected void process() {
        super.process();

        var changeConsumer = notNull(getNodeTree().getChangeConsumer());
        var defaultLayer = getDefaultLayer(changeConsumer);

        var emitter = createEmitterNode();
        emitter.addInfluencers(new ColorInfluencer(), new AlphaInfluencer(), new SizeInfluencer(0.1F, 0F));
        emitter.setEnabled(true);

        var treeNode = getNode();
        var parent = (Node) treeNode.getElement();

        if (defaultLayer != null) {
            SceneLayer.setLayer(defaultLayer, emitter);
        }

        changeConsumer.execute(new AddChildOperation(emitter, parent));
    }

    /**
     * Create an emitter particle node.
     *
     * @return the particle emitter node
     */
    @FxThread
    protected @NotNull ParticleEmitterNode createEmitterNode() {
        return new ParticleEmitterNode(EditorUtil.getAssetManager());
    }
}
