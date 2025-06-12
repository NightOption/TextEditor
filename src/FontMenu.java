import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FontMenu extends JDialog {
    // reference to the GUI so that changes could be made from this class
    private NotepadGUI source;

    public FontMenu(NotepadGUI source) {
        this.source = source;
        setTitle("Font Settings");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(425, 350);
        setLocationRelativeTo(source);
        // cannot interact with NotePad until Font dialog is closed
        setModal(true);

        // removes layout manager, giving more control on the placement of gui components for this dialog
        setLayout(null);

        addMenuComponents();

    }

    private void addMenuComponents() {
        addFontChooser();
        addFontStyleChooser();
        addFontSizeChooser();
    }

    private void addFontChooser() {
        JLabel fontLabel = new JLabel("Font:");
        fontLabel.setBounds(10, 5, 125, 10);
        add(fontLabel);

        JPanel fontPanel = new JPanel();
        fontPanel.setBounds(10, 15, 125, 160);

        JTextField currentFontField = new JTextField(source.getTextArea().getFont().getFontName());
        currentFontField.setPreferredSize(new Dimension(125, 25));
        currentFontField.setEditable(false);
        fontPanel.add(currentFontField);

        // display list of available Fonts
        JPanel listOfFontsPanel = new JPanel();

        // changes the layout to only show one column per font
        listOfFontsPanel.setLayout(new BoxLayout(listOfFontsPanel, BoxLayout.Y_AXIS));

        listOfFontsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(listOfFontsPanel);
        scrollPane.setPreferredSize(new Dimension(125, 125));

        // retrieve all possible fonts
        GraphicsEnvironment availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = availableFonts.getAvailableFontFamilyNames();

        //
        for (String fontName : fontNames) {
            JLabel fontNameLabel = new JLabel(fontName);

            fontNameLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    currentFontField.setText(fontName);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    fontNameLabel.setOpaque(true);
                    fontNameLabel.setBackground(Color.BLUE);
                    fontNameLabel.setForeground(Color.WHITE);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    fontNameLabel.setBackground(null);
                    fontNameLabel.setForeground(null);
                }
            });

            listOfFontsPanel.add(fontNameLabel);
        }

        fontPanel.add(listOfFontsPanel);

        add(fontPanel);
    }

    private void addFontStyleChooser() {
        JLabel fontStyleLabel = new JLabel("Font Style");
        fontStyleLabel.setBounds(145, 5, 125, 10);
        add(fontStyleLabel);

        JPanel fontStylePanel = new JPanel();
        fontStylePanel.setBounds(145, 15, 25, 160);

        int currentFontStyle = source.getTextArea().getFont().getStyle();
        String currentFontStyleText;

        switch (currentFontStyle) {
            case Font.PLAIN:
                currentFontStyleText = "Plain";
                break;
            case Font.BOLD:
                currentFontStyleText = "Bold";
                break;
            case Font.ITALIC:
                currentFontStyleText = "Italic";
                break;
            default:
                currentFontStyleText = "Bold Italic";
                break;
        }

        JTextField currentFontStyleField = new JTextField(currentFontStyleText);
        currentFontStyleField.setPreferredSize(new Dimension(125, 25));
        currentFontStyleField.setEditable(false);
        fontStylePanel.add(currentFontStyleField);

        // display available font styles
        JPanel listOfFontStylesPanel = new JPanel();
        listOfFontStylesPanel.setLayout(new BoxLayout(listOfFontStylesPanel, BoxLayout.Y_AXIS));
        listOfFontStylesPanel.setBackground(Color.WHITE);

        JLabel plainStyle = new JLabel("Plain");
        plainStyle.setFont(new Font("Dialog", Font.PLAIN, 12));
        plainStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(plainStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                plainStyle.setOpaque(true);
                plainStyle.setBackground(Color.BLUE);
                plainStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                plainStyle.setBackground(null);
                plainStyle.setForeground(null);
            }
        });
        listOfFontStylesPanel.add(plainStyle);

        JLabel boldStyle = new JLabel("Bold");
        boldStyle.setFont(new Font("Dialog", Font.BOLD, 12));
        boldStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(boldStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                boldStyle.setOpaque(true);
                boldStyle.setBackground(Color.BLUE);
                boldStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boldStyle.setBackground(null);
                boldStyle.setForeground(null);
            }
        });
        listOfFontStylesPanel.add(boldStyle);

        JLabel italicStyle = new JLabel("Italic");
        italicStyle.setFont(new Font("Dialog", Font.ITALIC, 12));
        italicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(italicStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                italicStyle.setOpaque(true);
                italicStyle.setBackground(Color.BLUE);
                italicStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                italicStyle.setBackground(null);
                italicStyle.setForeground(null);
            }
        });
        listOfFontStylesPanel.add(italicStyle);

        JLabel boldItalicStyle = new JLabel("Bold Italic");
        boldItalicStyle.setFont(new Font("Dialog", Font.BOLD | Font.ITALIC, 12));
        boldItalicStyle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                currentFontStyleField.setText(boldItalicStyle.getText());
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                boldItalicStyle.setOpaque(true);
                boldItalicStyle.setBackground(Color.BLUE);
                boldItalicStyle.setForeground(Color.WHITE);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                boldItalicStyle.setBackground(null);
                boldItalicStyle.setForeground(null);
            }
        });
        listOfFontStylesPanel.add(boldItalicStyle);

        JScrollPane scrollPane = new JScrollPane(listOfFontStylesPanel);
        scrollPane.setPreferredSize(new Dimension(125, 125));
        fontStylePanel.add(scrollPane);

        add(fontStylePanel);

    }

    private void addFontSizeChooser() {
        JLabel fontSizeLabel = new JLabel("Font Size: ");
        fontSizeLabel.setBounds(275, 5, 125, 10);
        add(fontSizeLabel);
    }
}
