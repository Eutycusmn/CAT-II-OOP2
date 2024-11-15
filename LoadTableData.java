private void loadTableData() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Registration");
            while (rs.next()) {
                Object[] row = {rs.getInt("id"), rs.getString("name"), rs.getString("gender"),
                        rs.getString("address"), rs.getString("contact")};
                tableModel.addRow(row);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void registerUser() {
        try {
            // Validate ID as an integer
            int id;
            try {
                id = Integer.parseInt(idField.getText().trim());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID must be a valid integer.", "Invalid ID", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Check if ID already exists
            PreparedStatement checkIdStmt = connection.prepareStatement("SELECT id FROM Registration WHERE id = ?");
            checkIdStmt.setInt(1, id);
            ResultSet rs = checkIdStmt.executeQuery();
            if (rs.next()) {
                JOptionPane.showMessageDialog(this, "ID already exists. Please enter a unique ID.", "Duplicate ID", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Get other form data
            String name = nameField.getText().trim();
            String gender = maleButton.isSelected() ? "Male" : (femaleButton.isSelected() ? "Female" : "");
            String address = addressField.getText().trim();
            String contact = contactField.getText().trim();

            // Insert the new user record
            PreparedStatement ps = connection.prepareStatement("INSERT INTO Registration (id, name, gender, address, contact) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setString(3, gender);
            ps.setString(4, address);
            ps.setString(5, contact);
            ps.executeUpdate();

            // Add the new row to the table model
            Object[] row = {id, name, gender, address, contact};
            tableModel.addRow(row);

            // Clear the form
            resetForm();

            JOptionPane.showMessageDialog(this, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "An error occurred while registering the user.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void resetForm() {
        idField.setText("");
        nameField.setText("");
        genderGroup.clearSelection();
        addressField.setText("");
        contactField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RegistrationForm form = new RegistrationForm();
            form.setVisible(true);
        });
    }
}
