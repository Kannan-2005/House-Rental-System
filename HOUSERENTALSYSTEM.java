import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class HouseRentalSystem {
    private static ArrayList<Property> properties = new ArrayList<>();
    private static DefaultTableModel propertyTableModel;
    private static DefaultTableModel requestTableModel;

    public static void main(String[] args) {
        JFrame frame = new JFrame("House Rental System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Landlord Panel
        JPanel landlordPanel = new JPanel(new BorderLayout());
        DefaultTableModel landlordPropertyModel = new DefaultTableModel(new String[]{"Rent", "Location"}, 0);
        JTable landlordPropertyTable = new JTable(landlordPropertyModel);

        JPanel requestPanel = new JPanel(new BorderLayout());
        requestTableModel = new DefaultTableModel(new String[]{"Property", "Tenant Request"}, 0);
        JTable requestTable = new JTable(requestTableModel);

        JButton acceptButton = new JButton("Accept Request");
        JButton rejectButton = new JButton("Reject Request");
        acceptButton.setBackground(Color.GREEN);
        rejectButton.setBackground(Color.RED);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(acceptButton);
        buttonPanel.add(rejectButton);

        requestPanel.add(new JScrollPane(requestTable), BorderLayout.CENTER);
        requestPanel.add(buttonPanel, BorderLayout.SOUTH);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(landlordPropertyTable), requestPanel);
        landlordPanel.add(splitPane, BorderLayout.CENTER);

        tabbedPane.add("Landlord", landlordPanel);

        // Tenant Panel
        JPanel tenantPanel = new JPanel(new BorderLayout());
        propertyTableModel = new DefaultTableModel(new String[]{"Rent", "Location"}, 0);
        JTable propertyTable = new JTable(propertyTableModel);
        JButton requestButton = new JButton("Request to Rent");
        requestButton.setBackground(Color.CYAN);

        tenantPanel.add(new JScrollPane(propertyTable), BorderLayout.CENTER);
        tenantPanel.add(requestButton, BorderLayout.SOUTH);

        tabbedPane.add("Tenant", tenantPanel);

        // Admin Panel
        JPanel adminPanel = new JPanel(new BorderLayout());
        JTable adminTable = new JTable(propertyTableModel);
        JButton removeButton = new JButton("Remove Property");
        removeButton.setBackground(Color.ORANGE);

        adminPanel.add(new JScrollPane(adminTable), BorderLayout.CENTER);
        adminPanel.add(removeButton, BorderLayout.SOUTH);

        tabbedPane.add("Admin", adminPanel);

        // Add tabbed pane to the frame
        frame.add(tabbedPane, BorderLayout.CENTER);

        // Event Listeners
        // Landlord: Add properties
        JButton addButton = new JButton("Add Property");
        addButton.addActionListener(e -> {
            String rent = JOptionPane.showInputDialog(frame, "Enter Rent:");
            String location = JOptionPane.showInputDialog(frame, "Enter Location:");
            if (rent != null && location != null && !rent.isEmpty() && !location.isEmpty()) {
                Property property = new Property(rent, location);
                properties.add(property);
                propertyTableModel.addRow(new Object[]{rent, location});
                landlordPropertyModel.addRow(new Object[]{rent, location});
                JOptionPane.showMessageDialog(frame, "Property added successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields.");
            }
        });
        landlordPanel.add(addButton, BorderLayout.NORTH);

        // Tenant: Request to rent
        requestButton.addActionListener(e -> {
            int selectedRow = propertyTable.getSelectedRow();
            if (selectedRow >= 0) {
                String rent = (String) propertyTableModel.getValueAt(selectedRow, 0);
                String location = (String) propertyTableModel.getValueAt(selectedRow, 1);
                String tenantName = JOptionPane.showInputDialog(frame, "Enter your name:");
                if (tenantName != null && !tenantName.isEmpty()) {
                    requestTableModel.addRow(new Object[]{location + " (Rent: " + rent + ")", tenantName});
                    JOptionPane.showMessageDialog(frame, "Request sent successfully!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Name cannot be empty.");
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a property to request.");
            }
        });

        // Landlord: Accept or reject requests
        acceptButton.addActionListener(e -> {
            int selectedRow = requestTable.getSelectedRow();
            if (selectedRow >= 0) {
                String request = (String) requestTableModel.getValueAt(selectedRow, 1);
                JOptionPane.showMessageDialog(frame, "Request from " + request + " accepted!");
                requestTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a request to accept.");
            }
        });

        rejectButton.addActionListener(e -> {
            int selectedRow = requestTable.getSelectedRow();
            if (selectedRow >= 0) {
                String request = (String) requestTableModel.getValueAt(selectedRow, 1);
                JOptionPane.showMessageDialog(frame, "Request from " + request + " rejected!");
                requestTableModel.removeRow(selectedRow);
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a request to reject.");
            }
        });

        // Admin: Remove properties
        removeButton.addActionListener(e -> {
            int selectedRow = adminTable.getSelectedRow();
            if (selectedRow >= 0) {
                propertyTableModel.removeRow(selectedRow);
                landlordPropertyModel.removeRow(selectedRow);
                properties.remove(selectedRow);
                JOptionPane.showMessageDialog(frame, "Property removed successfully.");
            } else {
                JOptionPane.showMessageDialog(frame, "Please select a property to remove.");
            }
        });

        frame.setVisible(true);
    }

    static class Property {
        String rent;
        String location;

        Property(String rent, String location) {
            this.rent = rent;
            this.location = location;
        }
    }
}

