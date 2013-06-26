package awk;

import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ClipBoard {
	public static void main(String args[]) {
		Transferable t = Toolkit.getDefaultToolkit().getSystemClipboard()
				.getContents(null);

		try {
			if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
				String text = (String) t
						.getTransferData(DataFlavor.stringFlavor);
				System.out.println("Current ClipBoard data is:\n" + text);
				text = "I changed clipboard text";
				StringSelection ss = new StringSelection(text);
				Toolkit.getDefaultToolkit().getSystemClipboard()
						.setContents(ss, null);
				System.out.println("Changed ClipBoard data is:\n" + text);
			}
		} catch (Exception e) {

		}
	}
}
