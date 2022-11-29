package Ctrl;

import Model.HDR;
import Model.InvTabel;
import Model.ITM;
import Model.ItemTabel;
import View.InvDialog;
import View.Frame1;
import View.ItmDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class CtrlAction1 implements ActionListener, ListSelectionListener {

    private Frame1 frame;
    private InvDialog invoiceDialog;
    private ItmDialog lineDialog;

    public CtrlAction1(Frame1 frame) {
        this.frame = frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        System.out.println("Action: " + actionCommand);
        switch (actionCommand) {
            //case "Load File":
             //   loadFile();
              //  break;
           // case "Save File":
             //   saveFile();
               // break;
            case "Create New Invoice":
                createNewInvoice();
                break;
            case "Delete Invoice":
                deleteInvoice();
                break;
            case "Create New Item":
                createNewItem();
                break;
            case "Delete Item":
                deleteItem();
                break;
            case "createInvoiceCancel":
                createInvoiceCancel();
                break;
            case "createInvoiceOK":
                createInvoiceOK();
                break;
            case "createLineOK":
                createLineOK();
                break;
            case "createLineCancel":
                createLineCancel();
                break;
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int selectedIndex = frame.getInvoiceTable().getSelectedRow();
        if (selectedIndex != -1) {
            System.out.println("You have selected row: " + selectedIndex);
            HDR currentInvoice = frame.getInvoices().get(selectedIndex);
            frame.getInvoiceNumLabel().setText("" + currentInvoice.getNum());
            frame.getInvoiceDateLabel().setText(currentInvoice.getDate());
            frame.getCustomerNameLabel().setText(currentInvoice.getCustomer());
            frame.getInvoiceTotalLabel().setText("" + currentInvoice.getInvoiceTotal());
            ItemTabel linesTableModel = new ItemTabel(currentInvoice.getLines());
            frame.getLineTable().setModel(linesTableModel);
            linesTableModel.fireTableDataChanged();
        }
    }

//    private void loadFile() {
//        JFileChooser fc = new JFileChooser();
//        try {
//            int result = fc.showOpenDialog(frame);
//            if (result == JFileChooser.APPROVE_OPTION) {
//                File headerFile = fc.getSelectedFile();
//                Path headerPath = Paths.get(headerFile.getAbsolutePath());
//                List<String> headerLines = Files.readAllLines(headerPath);
//                System.out.println("Invoices have been read");
//                
//                ArrayList<HDR> invoicesArray = new ArrayList<>();
//                for (String headerLine : headerLines) {
//                    try {
//                    String[] headerParts = headerLine.split(",");
//                    int invoiceNum = Integer.parseInt(headerParts[0]);
//                    String invoiceDate = headerParts[1];
//                    String customerName = headerParts[2];
//
//                    HDR invoice = new HDR(invoiceNum, invoiceDate, customerName);
//                    invoicesArray.add(invoice);
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                        JOptionPane.showMessageDialog(frame, "Wrong file format", "ERROR", JOptionPane.ERROR_MESSAGE);
//                    }
//                }
//                System.out.println("Check point");
//                result = fc.showOpenDialog(frame);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File lineFile = fc.getSelectedFile();
//                    Path linePath = Paths.get(lineFile.getAbsolutePath());
//                    List<String> lineLines = Files.readAllLines(linePath);
//                    System.out.println("Lines have been read");
//                    for (String lineLine : lineLines) {
//                        try {
//                        String lineParts[] = lineLine.split(",");
//                        int invoiceNum = Integer.parseInt(lineParts[0]);
//                        String itemName = lineParts[1];
//                        double itemPrice = Double.parseDouble(lineParts[2]);
//                        int count = Integer.parseInt(lineParts[3]);
//                        HDR inv = null;
//                        for (HDR invoice : invoicesArray) {
//                            if (invoice.getNum() == invoiceNum) {
//                                inv = invoice;
//                                break;
//                            }
//                        }
//
//                        ITM line = new ITM(itemName, itemPrice, count, inv);
//                        inv.getLines().add(line);
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        JOptionPane.showMessageDialog(frame, "Wrong file format", "ERROR", JOptionPane.ERROR_MESSAGE);
//                        }
//                    }
//                    System.out.println("Check point");
//                }
//                frame.setInvoices(invoicesArray);
//                InvTabel invoicesTableModel = new InvTabel(invoicesArray);
//                frame.setInvoicesTableModel(invoicesTableModel);
//                frame.getInvoiceTable().setModel(invoicesTableModel);
//                frame.getInvoicesTableModel().fireTableDataChanged();
//            }
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            JOptionPane.showMessageDialog(frame, "Cannot read file", "ERROR", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private void saveFile() {
//        ArrayList<HDR> invoices = frame.getInvoices();
//        String headers = "";
//        String lines = "";
//        for (HDR invoice : invoices) {
//            String invCSV = invoice.getAsCSV();
//            headers += invCSV;
//            headers += "\n";
//
//            for (ITM line : invoice.getLines()) {
//                String lineCSV = line.getAsCSV();
//                lines += lineCSV;
//                lines += "\n";
//            }
//        }
//        System.out.println("Check point");
//        try {
//            JFileChooser fc = new JFileChooser();
//            int result = fc.showSaveDialog(frame);
//            if (result == JFileChooser.APPROVE_OPTION) {
//                File headerFile = fc.getSelectedFile();
//                FileWriter hfw = new FileWriter(headerFile);
//                hfw.write(headers);
//                hfw.flush();
//                hfw.close();
//                result = fc.showSaveDialog(frame);
//                if (result == JFileChooser.APPROVE_OPTION) {
//                    File lineFile = fc.getSelectedFile();
//                    FileWriter lfw = new FileWriter(lineFile);
//                    lfw.write(lines);
//                    lfw.flush();
//                    lfw.close();
//                }
//            }
//        } catch (Exception ex) {
//
//        }
//    }

    private void createNewInvoice() {
        invoiceDialog = new InvDialog(frame);
        invoiceDialog.setVisible(true);
    }

    private void deleteInvoice() {
        int selectedRow = frame.getInvoiceTable().getSelectedRow();
        if (selectedRow != -1) {
            frame.getInvoices().remove(selectedRow);
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createNewItem() {
        lineDialog = new ItmDialog(frame);
        lineDialog.setVisible(true);
    }

    private void deleteItem() {
        int selectedRow = frame.getLineTable().getSelectedRow();

        if (selectedRow != -1) {
            ItemTabel linesTableModel = (ItemTabel) frame.getLineTable().getModel();
            linesTableModel.getLines().remove(selectedRow);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
    }

    private void createInvoiceCancel() {
        invoiceDialog.setVisible(false);
        invoiceDialog.dispose();
        invoiceDialog = null;
    }

    private void createInvoiceOK() {
        DateFormat df = new SimpleDateFormat("dd/MM/YYYY");
        String date = invoiceDialog.getInvDateField().getText();
        String customer = invoiceDialog.getCustNameField().getText();
        int num = frame.getNextInvoiceNum();
        try {
            df.parse(date);
            String [] dateParts = date.split("/");
            if (dateParts.length < 3) {
                JOptionPane.showMessageDialog(frame, "Wrong date format", "ERROR", JOptionPane.ERROR_MESSAGE);
            } else {
                int day = Integer.parseInt(dateParts[0]);
                int month = Integer.parseInt(dateParts[1]);
                int year = Integer.parseInt(dateParts[2]);
                if (day > 31 || month > 12) {
                    JOptionPane.showMessageDialog(frame, "Wrong date format", "ERROR", JOptionPane.ERROR_MESSAGE);
                } else {
                HDR invoice = new HDR(num, date, customer);
                frame.getInvoices().add(invoice);
                frame.getInvoicesTableModel().fireTableDataChanged();
                invoiceDialog.setVisible(false);
                invoiceDialog.dispose();
                invoiceDialog = null;  
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Wrong date format", "ERROR", JOptionPane.ERROR_MESSAGE);
            
        }
        
    }

    private void createLineOK() {
        String item = lineDialog.getItemNameField().getText();
        String countStr = lineDialog.getItemCountField().getText();
        String priceStr = lineDialog.getItemPriceField().getText();
        int count = Integer.parseInt(countStr);
        double price = Double.parseDouble(priceStr);
        int selectedInvoice = frame.getInvoiceTable().getSelectedRow();
        if (selectedInvoice != -1) {
            HDR invoice = frame.getInvoices().get(selectedInvoice);
            ITM line = new ITM(item, price, count, invoice);
            invoice.getLines().add(line);
            ItemTabel linesTableModel = (ItemTabel) frame.getLineTable().getModel();
            //linesTableModel.getLines().add(line);
            linesTableModel.fireTableDataChanged();
            frame.getInvoicesTableModel().fireTableDataChanged();
        }
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

    private void createLineCancel() {
        lineDialog.setVisible(false);
        lineDialog.dispose();
        lineDialog = null;
    }

}
