import javax.swing.JOptionPane;

public class Driver {
	public static void main(String[] run) {
		int rX = 1920;
		int rY = 1080;
		int f;
		while (true) {
			try {
				String fulls = JOptionPane.showInputDialog(null, "Fullscreen(1=fullscreen/ 0=no)");
				
				if (fulls == null) {
					System.exit(0);
				}
				f = Integer.parseInt(fulls);
				System.out.println(f);
				break;
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, "Please, enter a valid number(0=no/ 1=fullscreen)");
			}
		}
		
		if(f == 0) {
			while (true) {
				try {
					
					String resX = JOptionPane.showInputDialog(null, "What X Resolution?");
					if (resX == null) {
						System.exit(0);
					}
					rX = Integer.parseInt(resX);
					System.out.println(rX);
					break;
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please, enter a valid number");
				}
			}
			while (true) {
				try {
					String resY = JOptionPane.showInputDialog(null, "What Y Resolution?");
					if (resY == null) {
						System.exit(0);
					}
					rY = Integer.parseInt(resY);
					System.out.println(rY);
					break;

				} catch (Exception ex) {
					JOptionPane.showMessageDialog(null, "Please, enter a valid number");
				}
			}
		}
		
	new Frame(rX, rY, f);

	}
}
