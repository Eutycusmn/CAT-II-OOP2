import javax.swing.*;
import java.awt.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

class RegistrationForm extends JFrame {
    // Swing components
    private final JTextField idField, nameField, addressField, contactField;
    private final JRadioButton maleButton, femaleButton;
    private final DefaultTableModel tableModel;
    private final ButtonGroup genderGroup;

    // Database connection
    private Connection connection;

    public RegistrationForm() {
        // Initialize the form
        setTitle("Registration Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Left panel for form fields
        JPanel formPanel = new JPanel(new GridLayout(6, 2));
        formPanel.setBorder(BorderFactory.createTitledBorder("Registration Form"));

        // Add fields to form panel
        formPanel.add(new JLabel("ID"));
        idField = new JTextField();
        formPanel.add(idField);

        formPanel.add(new JLabel("Name"));
        nameField = new JTextField();
        formPanel.add(nameField);

        formPanel.add(new JLabel("Gender"));
        maleButton = new JRadioButton("Male");
        femaleButton = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);
        JPanel genderPanel = new JPanel(new FlowLayout());
        genderPanel.add(maleButton);
        genderPanel.add(femaleButton);
        formPanel.add(genderPanel);

        formPanel.add(new JLabel("Address"));
        addressField = new JTextField();
        formPanel.add(addressField);

        formPanel.add(new JLabel("Contact"));
        contactField = new JTextField();
        formPanel.add(contactField);

        // Buttons
        JButton submitButton = new JButton("Register");
        JButton resetButton = new JButton("Reset");
        JButton exitButton = new JButton("Exit");
        submitButton.addActionListener(_ -> registerUser());
        resetButton.addActionListener(_ -> resetForm());
        exitButton.addActionListener(_ -> System.exit(0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exitButton);

        // Right panel for displaying data
        String[] columns = {"ID", "Name", "Gender", "Address", "Contact"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable dataTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(dataTable);

        // Add panels to frame
        add(formPanel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);
        add(tableScrollPane, BorderLayout.CENTER);

        // Initialize database connection
        connectDatabase();
        loadTableData();
    }

    private void connectDatabase() {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Database URL, username, and password
            String url = "jdbc:mysql://localhost:3306/Registration";
            String user = "root";
            String password = "";

            // Establish the connection
            connection = DriverManager.getConnection(url, user, password);

            System.out.println("Connected to the database successfully!");
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("Failed to connect to the database.");
        }
    }
