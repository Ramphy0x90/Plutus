package hhccco.plutus.interfaces;

/**
 * Interface for containers structure
 */
public interface Struct {
    /*
     This method is used to set the structure of the object
     (Set stage, scene, pane layout, grid columns / rows, etc)
     */
    default void setStruct() {}

    /*
    This method is used to initialize the node objects of the parent
     */
    default void initNodes() {}
}
