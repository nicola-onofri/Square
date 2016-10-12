package pajc.config;

import java.awt.Component;
import java.awt.event.WindowEvent;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

public class InterfaceHelpers {

	// Clean Screen
	public static void cls(JPanel contentPane){
		Component[] components = contentPane.getComponents();
	    for (Component component : components) {
	        if (component instanceof JTextField || component instanceof JTextArea) {
	            JTextComponent specificObject = (JTextComponent) component;
	            specificObject.setText("");
	        }
	    }
	}
	
	// Close Parent JFrame
	public static void closeParent(JComponent comp){
		JFrame frame = (JFrame)SwingUtilities.getRoot(comp);
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	}

	
	// Remove Selected JTable Rows
	public static void removeSelectedRows(JTable table){
		   DefaultTableModel model = (DefaultTableModel) table.getModel();
		   int[] rows = table.getSelectedRows();
		   for(int i=0;i<rows.length;i++){
			   System.out.println(rows[i]);
		     model.removeRow(rows[i]-i);
		   }
		   table.getSelectionModel().clearSelection();
	}
	
	// Reset Checkboxes
	public static void cleanCheckboxes(JPanel contentPane){
		Component[] components = contentPane.getComponents();
	    for (Component component : components) {
	    	if (component instanceof JCheckBox) {
	    		JCheckBox specificObj = (JCheckBox) component;
	    		specificObj.setSelected(false);
	    	}
	    }
	}
	
}
