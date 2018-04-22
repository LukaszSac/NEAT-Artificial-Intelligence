package genotyp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Pool
{
    public static final int INPUT_LAYER = 0;
    public static final int OUTPUT_LAYER = 1;
    private static int layerId=0;
    private static int nodeId=0;
    private static int connectionId=0;
    private class Node
    {
        private int id;
        private NodeType type = null;
        private Layer layer;
        private HashMap<Integer,ArrayList<Node>> connectedTo= null;
        public Node(NodeType type, Layer layer)
        {
            id = nodeId++;
            this.type = type;
            this.layer = layer;
            connectedTo = new HashMap<>();
        }

        @Override
        public boolean equals(Object obj)
        {
            if(obj instanceof Node)
            {
                if(((Node) obj).id==id) return true;
            }
            return false;
        }

        public int getId() {
            return id;
        }

        public void addConnection(Node node)
        {
            int layedId = node.getLayer().getId();
            ArrayList<Node> nodes = connectedTo.get(layerId);
            if(nodes==null)
                connectedTo.put(layerId,nodes = new ArrayList<Node>());
            nodes.add(node);
        }

        public Layer getLayer() {
            return layer;
        }
    }

    private class Layer implements Iterable<Node>
    {
        private ArrayList <Node> nodes;
        private int id;
        public Layer()
        {
            id=layerId++;
            nodes = new ArrayList<>();
        }

        public void addNodeToLayer(Node node)
        {
            nodes.add(node);
        }

        public int getId() {
            return id;
        }

        @Override
        public Iterator<Node> iterator() {
            return new Iterator<Node>() {
                private int kursor = 0;
                @Override
                public boolean hasNext() {
                    return nodes.size() > kursor;
                }

                @Override
                public Node next() {
                    if(this.hasNext()) {
                        Node node = nodes.get(kursor);
                        kursor ++;
                        return node;
                    }
                    throw new NoSuchElementException();
                }
            };
        }
    }

    private class Connection
    {
        private int id;
        private Node from, to;
        private InformationPackageNewConnection infPackage = null;
        public Connection(Node from, Node to)
        {
            this.from = from;
            this.to = to;
            this.id = connectionId++;
            infPackage = new InformationPackageNewConnection();
            infPackage.setIdInnovation(id);
            infPackage.setFrom(from.getId());
            infPackage.setFromLayer(from.getLayer().getId());
            infPackage.setTo(to.getId());
            infPackage.setToLayer(to.getLayer().getId());
            //System.out.println("Check in Pool, Id: " + id + " from: " + from.getId() + " Layer: " + from.getLayer().getId() + " To: " + to.getId() + " Layer:" + to.getLayer().getId());
        }

        public int getId()
        {
            return this.id;
        }

        public Node getFrom() {
            return from;
        }

        public Node getTo() {
            return to;
        }

        public InformationPackageNewConnection getInfPackage() {
            InformationPackageNewConnection e = infPackage;
            //System.out.println("Check in pool - sending, Id: " + e.getIdInnovation() + " from: " + e.getFrom() + " Layer: " + e.getFromLayer() + " To: " + e.getTo() + " Layer: " + e.getToLayer());
            return infPackage;
        }
    }

    private static Pool instance;
    private HashMap<Integer,Node> nodes = null;
    private HashMap<Integer,Layer> layers = null;
    private ArrayList<Layer> layersOrder = null;
    private HashMap<Integer,Connection> connections = null;
    private int noOfNodes = 0;
    private Pool()
    {
        connections = new HashMap<>();
        nodes = new HashMap<>();
        layers = new HashMap<>();
        layersOrder = new ArrayList<>();
        init();
    }

    public int getNoOfNodes() {
        return noOfNodes;
    }

    public static Pool getInstance()
    {
        if(instance==null) instance = new Pool();
        return instance;
    }

    private void init()
    {
        //stwórz warste input, output;
        layersOrder.add(createrLayer());
        layersOrder.add(createrLayer());
    }

    private Layer createrLayer()
    {
        Layer layer = new Layer();
        layers.put(layer.getId(),layer);
        return layer;
    }

    public ArrayList<Integer> getLayers()
    {
        ArrayList<Integer> ids = new ArrayList<>();
        for(Layer layerCursor : layersOrder)
        {
            ids.add(layerCursor.getId());
        }
        return ids;
    }

    public ArrayList<Integer> getLayersNodesIds(int layerId)
    {
        ArrayList<Integer> ids = new ArrayList<>();
        Layer layer = layers.get(layerId);
        for(Node node : layer)
        {
            ids.add(node.getId());
        }
        return ids;
    }

    public int addLayer(int previousLayerId)
    {
        Layer layer = createrLayer();
        int cursor = 1;
        for(Layer layerCursor : layersOrder)
        {
            if(layerCursor.getId()==previousLayerId) break;
            else cursor++;
        }
        layersOrder.add(cursor,layer);
        return layer.getId();
    }

    public int addNode( int layerId)
    {
        NodeType type = null;
        if(layerId==Pool.INPUT_LAYER) type = NodeType.input;
        else if(layerId==Pool.OUTPUT_LAYER) type = NodeType.output;
        else type = NodeType.hidden;
        Node newNode = new Node(type,layers.get(layerId));
        nodes.put(newNode.getId(),newNode);
        layers.get(layerId).addNodeToLayer(newNode);
        noOfNodes++;
        return newNode.getId();
    }

    private Connection newConnection(int from, int to)
    {
        for(Connection connection : connections.values())
        {
            if(from==connection.from.id&&to==connection.to.id) return connection;
        }
        Node nodeFrom = nodes.get(from);
        Node nodeTo = nodes.get(to);
        if(nodeFrom.getLayer()==nodeTo.getLayer()) return null;
        Connection connection = new Connection(nodeFrom,nodeTo);
        nodes.get(from).addConnection(nodeTo);
        connections.put(connection.getId(),connection);
        return connection;
    }

    public InformationPackageNewConnection addConnection(int from, int to)
    {
        return newConnection(from,to).getInfPackage();
    }

    private String getNodeType(Node node)
    {
        if(node.type==NodeType.input) return "Input";
        if(node.type==NodeType.hidden) return "Hidden";
        return "Output";
    }

    public String toString()
    {
        String output = "";
        for(Connection connection : connections.values())
        {
            output += "Id: " + connection.getId() + ", From(";
            output+=getNodeType(connection.getFrom());
            output+= "): " + connection.getFrom().getId() + ", To(";
            output+=getNodeType(connection.getTo());
            output+="): " + connection.getTo().getId() + "\n";
        }
        return output;
    }
}
