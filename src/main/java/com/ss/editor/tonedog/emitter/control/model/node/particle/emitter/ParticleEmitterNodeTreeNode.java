package com.ss.editor.tonedog.emitter.control.model.node.particle.emitter;

import com.ss.editor.Messages;
import com.ss.editor.annotation.FxThread;
import com.ss.editor.tonedog.emitter.control.model.tree.action.mesh.CreateImpostorParticleMeshAction;
import com.ss.editor.tonedog.emitter.control.model.tree.action.mesh.CreatePointParticleMeshAction;
import com.ss.editor.tonedog.emitter.control.model.tree.action.mesh.CreateQuadParticleMeshAction;
import com.ss.editor.tonedog.emitter.control.model.tree.action.mesh.LoadModelParticlesMeshAction;
import com.ss.editor.tonedog.emitter.control.model.tree.action.shape.*;
import com.ss.editor.tonedog.emitter.model.node.particles.ParticleInfluencers;
import com.ss.editor.ui.Icons;
import com.ss.editor.ui.control.model.node.spatial.NodeTreeNode;
import com.ss.editor.ui.control.model.tree.ModelNodeTree;
import com.ss.editor.ui.control.tree.NodeTree;
import com.ss.editor.ui.control.tree.node.TreeNode;
import com.ss.rlib.util.array.Array;
import com.ss.rlib.util.array.ArrayFactory;
import javafx.collections.ObservableList;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import tonegod.emitter.EmitterMesh;
import tonegod.emitter.ParticleEmitterNode;

/**
 * The implementation of the {@link NodeTreeNode} to represent the {@link ParticleEmitterNode} in the editor.
 *
 * @author JavaSaBr
 */
public class ParticleEmitterNodeTreeNode extends NodeTreeNode<ParticleEmitterNode> {

    public ParticleEmitterNodeTreeNode(@NotNull final ParticleEmitterNode element, final long objectId) {
        super(element, objectId);
    }

    @Override
    @FxThread
    public @Nullable Image getIcon() {
        return Icons.PARTICLES_16;
    }

    @Override
    @FxThread
    protected @Nullable Menu createToolMenu(@NotNull final NodeTree<?> nodeTree) {
        return null;
    }

    @Override
    @FxThread
    protected @Nullable Menu createCreationMenu(@NotNull final NodeTree<?> nodeTree) {
        return null;
    }

    @Override
    @FxThread
    public void fillContextMenu(@NotNull final NodeTree<?> nodeTree,
                                @NotNull final ObservableList<MenuItem> items) {
        if (!(nodeTree instanceof ModelNodeTree)) return;

        final Menu changeMeshMenu = new Menu(Messages.MODEL_NODE_TREE_ACTION_PARTICLE_EMITTER_CHANGE_PARTICLES_MESH, new ImageView(Icons.MESH_16));
        final ObservableList<MenuItem> subItems = changeMeshMenu.getItems();
        subItems.add(new CreateQuadParticleMeshAction(nodeTree, this));
        subItems.add(new CreatePointParticleMeshAction(nodeTree, this));
        subItems.add(new CreateImpostorParticleMeshAction(nodeTree, this));
        subItems.add(new LoadModelParticlesMeshAction(nodeTree, this));

        final Menu jmePrimitivesMenu = new Menu(Messages.MODEL_NODE_TREE_ACTION_CREATE_PRIMITIVE, new ImageView(Icons.GEOMETRY_16));
        final ObservableList<MenuItem> primitivesItems = jmePrimitivesMenu.getItems();
        primitivesItems.add(new CreateBoxShapeEmitterAction(nodeTree, this));
        primitivesItems.add(new CreateCylinderShapeEmitterAction(nodeTree, this));
        primitivesItems.add(new CreateDomeShapeEmitterAction(nodeTree, this));
        primitivesItems.add(new CreateQuadShapeEmitterAction(nodeTree, this));
        primitivesItems.add(new CreateSphereShapeEmitterAction(nodeTree, this));
        primitivesItems.add(new CreateTorusShapeEmitterAction(nodeTree, this));

        final Menu changeShapeMenu = new Menu(Messages.MODEL_NODE_TREE_ACTION_PARTICLE_EMITTER_CHANGE_SHAPE, new ImageView(Icons.GEOMETRY_16));
        changeShapeMenu.getItems().addAll(new CreateTriangleShapeEmitterAction(nodeTree, this),
                jmePrimitivesMenu,
                new LoadModelShapeEmitterAction(nodeTree, this));

        items.add(changeShapeMenu);
        items.add(changeMeshMenu);

        super.fillContextMenu(nodeTree, items);
    }

    @Override
    @FxThread
    public @NotNull Array<TreeNode<?>> getChildren(@NotNull final NodeTree<?> nodeTree) {

        final ParticleEmitterNode element = getElement();
        final EmitterMesh emitterShape = element.getEmitterShape();

        final Array<TreeNode<?>> children = ArrayFactory.newArray(TreeNode.class);
        children.add(FACTORY_REGISTRY.createFor(new ParticleInfluencers(element)));
        children.add(FACTORY_REGISTRY.createFor(emitterShape.getMesh()));

        return children;
    }
}
