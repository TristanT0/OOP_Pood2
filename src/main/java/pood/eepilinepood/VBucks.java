package pood.eepilinepood;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class VBucks extends Pane {
    private double spacing;

    public VBucks() {
        this(0);
    }

    public VBucks(double spacing) {
        this.spacing = spacing;
        setPadding(new Insets(spacing));
    }

    public void setSpacing(double spacing) {
        this.spacing = spacing;
        setPadding(new Insets(spacing));
    }

    public double getSpacing() {
        return spacing;
    }

    public void add(Node... nodes) {
        for (Node node : nodes) {
            getChildren().add(node);
        }
    }

    public void remove(Node node) {
        getChildren().remove(node);
    }

    public void clear() {
        getChildren().clear();
    }

    @Override
    protected void layoutChildren() {
        double x = getPadding().getLeft();
        double y = getPadding().getTop();
        double maxWidth = getWidth() - getPadding().getLeft() - getPadding().getRight();

        for (Node node : getChildren()) {
            double prefHeight = node.prefHeight(maxWidth);
            node.resizeRelocate(x, y, maxWidth, prefHeight);
            y += prefHeight + spacing;
        }
    }
}
