package application.model;

public class VendaModel {

    private int id;
    private int clienteId;
    private String data;
    private double total;

    public VendaModel(int clienteId, String data, double total) {
        this.clienteId = clienteId;
        this.data = data;
        this.total = total;
    }

    public int getClienteId() { return clienteId; }
    public String getData() { return data; }
    public double getTotal() { return total; }
}
