package dinphil;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import java.io.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;

 class Philosopher extends Thread
 {
     private JPanel left;
     private JPanel right;
     private Color color;
     private JTextPane log;

     public void Philosopher(int i) throws InterruptedException

     {
        PickingUpFork(i);
         System.out.println("Philosopher " + i + " is taking the forks");
         sleep(100);
         System.out.println("Philosopher " + i + " is eating");
         sleep(250);
         System.out.println("Philosopher " + i + " is putting down the forks");
         sleep(100);
         PuttingDownFork(i);

     }
     
     public void setForks(JPanel left, JPanel right) {
         this.left = left;
         this.right = right;
     }
     
     public void setColor(Color color) {
         this.color = color;
     }
     
     public JPanel getLeft() {
         return left;
     }
     
     public JPanel getRight() {
         return right;
     }
     
     public Color getColor() {
         return color;
     }
     


     public void PickingUpFork(int i)

     {
        while(DiningPhilosophers.mutexCheck <= 0)
        {
            System.out.println("Philosopher " + i + " is being tested for their forks");


        }
         DiningPhilosophers.philosopherStatus[i]="HUNGRY";

         statusTesting(i);

         DiningPhilosophers.mutexCheck=1;


         while(DiningPhilosophers.abilityToEat[i] <= 0 )
         {

             System.out.println("Philosopher " + i + " is waiting and thinking while his comrades use their forks");

         }
         
         getLeft().setBackground(getColor());
         getRight().setBackground(getColor());
         getLeft().repaint();
         getRight().repaint();

     }

     public void PuttingDownFork(int i)

     {
         while(DiningPhilosophers.mutexCheck <= 0)
         {
             System.out.println("Philosopher " + i + " is being tested for their forks");


         }

         DiningPhilosophers.philosopherStatus[i] = "THINKING";

         statusTesting((i + 4) % 5);
         statusTesting((i + 1) % 5);

         DiningPhilosophers.mutexCheck = 1;
         
         getLeft().setBackground(Color.LIGHT_GRAY);
         getRight().setBackground(Color.LIGHT_GRAY);
         getLeft().repaint();
         getRight().repaint();
     }
     
     public void run()

     {

         DiningPhilosophers.count++;
         Philosopher(DiningPhilosophers.count);

     }



     public void statusTesting(int i)

     {
         if(DiningPhilosophers.philosopherStatus[i] == "HUNGRY" && DiningPhilosophers.philosopherStatus[(i + 4) % 5] != "CAN_EAT" && DiningPhilosophers.philosopherStatus[(i + 1) % 5]!="CAN_EAT")
         {

             DiningPhilosophers.philosopherStatus[i] = "CAN_EAT";
             DiningPhilosophers.abilityToEat[i] = 1;             

         }

     }
 }


    public class DiningPhilosophers

    {

        static int abilityToEat[] = {0, 0, 0, 0, 0};
        static int count = -1;
        static int mutexCheck = 1;
        static String philosopherStatus[] = {"THINKING", "THINKING", "THINKING", "THINKING", "THINKING"};
        static JButton reset;

        public static void main (String args[])


        {
                        
            Dining din = new Dining();
            din.setVisible(true);
            JPanel[] forks = din.getForks();
            MessageConsole mc = new MessageConsole(din.getLog());
            mc.redirectOut();
            mc.redirectErr(Color.RED, null);
            mc.setMessageLines(100);

            
            Philosopher philosopherOne = new Philosopher();
            philosopherOne.setForks(forks[0], forks[4]);
            philosopherOne.setColor(Color.BLUE);
            Philosopher philosopherTwo = new Philosopher();
            philosopherTwo.setForks(forks[1], forks[0]);
            philosopherTwo.setColor(Color.RED);
            Philosopher philosopherThree = new Philosopher();
            philosopherThree.setForks(forks[2], forks[1]);
            philosopherThree.setColor(Color.GREEN);
            Philosopher philosopherFour = new Philosopher();
            philosopherFour.setForks(forks[3], forks[2]);
            philosopherFour.setColor(Color.YELLOW);
            Philosopher philosopherFive = new Philosopher();
            philosopherFive.setForks(forks[4], forks[3]);
            philosopherFive.setColor(Color.MAGENTA);
            
            

            philosopherOne.start();
            philosopherTwo.start();
            philosopherThree.start();
            philosopherFour.start();
            philosopherFive.start();


            reset = din.getBtn();
            reset.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    count = -1;
                    mutexCheck = 1;
                    for(int i = 0; i < abilityToEat.length; i++) {
                        abilityToEat[i] = 0;
                    }
                    for(int i = 0; i < philosopherStatus.length; i++) {
                        philosopherStatus[i] = "Hungry";
                    }
                    Philosopher philosopherOne = new Philosopher();
                    philosopherOne.setForks(forks[0], forks[4]);
                    philosopherOne.setColor(Color.BLUE);
                    Philosopher philosopherTwo = new Philosopher();
                    philosopherTwo.setForks(forks[1], forks[0]);
                    philosopherTwo.setColor(Color.RED);
                    Philosopher philosopherThree = new Philosopher();
                    philosopherThree.setForks(forks[2], forks[1]);
                    philosopherThree.setColor(Color.GREEN);
                    Philosopher philosopherFour = new Philosopher();
                    philosopherFour.setForks(forks[3], forks[2]);
                    philosopherFour.setColor(Color.YELLOW);
                    Philosopher philosopherFive = new Philosopher();
                    philosopherFive.setForks(forks[4], forks[3]);
                    philosopherFive.setColor(Color.MAGENTA);
                    philosopherOne.start();
                    philosopherTwo.start();
                    philosopherThree.start();
                    philosopherFour.start();
                    philosopherFive.start();
                }
        });
        }
        




    }



/*
 *  Create a simple console to display text messages.
 *
 *  Messages can be directed here from different sources. Each source can
 *  have its messages displayed in a different color.
 *
 *  Messages can either be appended to the console or inserted as the first
 *  line of the console
 *
 *  You can limit the number of lines to hold in the Document.
 */
class MessageConsole
{
	private JTextComponent textComponent;
	private Document document;
	private boolean isAppend;
	private DocumentListener limitLinesListener;

	public MessageConsole(JTextComponent textComponent)
	{
		this(textComponent, true);
	}

	/*
	 *	Use the text component specified as a simply console to display
	 *  text messages.
	 *
	 *  The messages can either be appended to the end of the console or
	 *  inserted as the first line of the console.
	 */
	public MessageConsole(JTextComponent textComponent, boolean isAppend)
	{
		this.textComponent = textComponent;
		this.document = textComponent.getDocument();
		this.isAppend = isAppend;
		textComponent.setEditable( false );
	}

	/*
	 *  Redirect the output from the standard output to the console
	 *  using the default text color and null PrintStream
	 */
	public void redirectOut()
	{
		redirectOut(null, null);
	}

	/*
	 *  Redirect the output from the standard output to the console
	 *  using the specified color and PrintStream. When a PrintStream
	 *  is specified the message will be added to the Document before
	 *  it is also written to the PrintStream.
	 */
	public void redirectOut(Color textColor, PrintStream printStream)
	{
		ConsoleOutputStream cos = new ConsoleOutputStream(textColor, printStream);
		System.setOut( new PrintStream(cos, true) );
	}

	/*
	 *  Redirect the output from the standard error to the console
	 *  using the default text color and null PrintStream
	 */
	public void redirectErr()
	{
		redirectErr(null, null);
	}

	/*
	 *  Redirect the output from the standard error to the console
	 *  using the specified color and PrintStream. When a PrintStream
	 *  is specified the message will be added to the Document before
	 *  it is also written to the PrintStream.
	 */
	public void redirectErr(Color textColor, PrintStream printStream)
	{
		ConsoleOutputStream cos = new ConsoleOutputStream(textColor, printStream);
		System.setErr( new PrintStream(cos, true) );
	}

	/*
	 *  To prevent memory from being used up you can control the number of
	 *  lines to display in the console
	 *
	 *  This number can be dynamically changed, but the console will only
	 *  be updated the next time the Document is updated.
	 */
	public void setMessageLines(int lines)
	{
		if (limitLinesListener != null)
			document.removeDocumentListener( limitLinesListener );

		limitLinesListener = new LimitLinesDocumentListener(lines, isAppend);
		document.addDocumentListener( limitLinesListener );
	}

	/*
	 *	Class to intercept output from a PrintStream and add it to a Document.
	 *  The output can optionally be redirected to a different PrintStream.
	 *  The text displayed in the Document can be color coded to indicate
	 *  the output source.
	 */
	class ConsoleOutputStream extends ByteArrayOutputStream
	{
		private final String EOL = System.getProperty("line.separator");
		private SimpleAttributeSet attributes;
		private PrintStream printStream;
		private StringBuffer buffer = new StringBuffer(80);
		private boolean isFirstLine;

		/*
		 *  Specify the option text color and PrintStream
		 */
		public ConsoleOutputStream(Color textColor, PrintStream printStream)
		{
			if (textColor != null)
			{
				attributes = new SimpleAttributeSet();
				StyleConstants.setForeground(attributes, textColor);
			}

			this.printStream = printStream;

			if (isAppend)
				isFirstLine = true;
		}

		/*
		 *  Override this method to intercept the output text. Each line of text
		 *  output will actually involve invoking this method twice:
		 *
		 *  a) for the actual text message
		 *  b) for the newLine string
		 *
		 *  The message will be treated differently depending on whether the line
		 *  will be appended or inserted into the Document
		 */
		public void flush()
		{
			String message = toString();

			if (message.length() == 0) return;

			if (isAppend)
			    handleAppend(message);
			else
			    handleInsert(message);

			reset();
		}

		/*
		 *	We don't want to have blank lines in the Document. The first line
		 *  added will simply be the message. For additional lines it will be:
		 *
		 *  newLine + message
		 */
		private void handleAppend(String message)
		{
			//  This check is needed in case the text in the Document has been
			//	cleared. The buffer may contain the EOL string from the previous
			//  message.

			if (document.getLength() == 0)
				buffer.setLength(0);

			if (EOL.equals(message))
			{
				buffer.append(message);
			}
			else
			{
				buffer.append(message);
				clearBuffer();
			}

		}
		/*
		 *  We don't want to merge the new message with the existing message
		 *  so the line will be inserted as:
		 *
		 *  message + newLine
		 */
		private void handleInsert(String message)
		{
			buffer.append(message);

			if (EOL.equals(message))
			{
				clearBuffer();
			}
		}

		/*
		 *  The message and the newLine have been added to the buffer in the
		 *  appropriate order so we can now update the Document and send the
		 *  text to the optional PrintStream.
		 */
		private void clearBuffer()
		{
			//  In case both the standard out and standard err are being redirected
			//  we need to insert a newline character for the first line only

			if (isFirstLine && document.getLength() != 0)
			{
			    buffer.insert(0, "\n");
			}

			isFirstLine = false;
			String line = buffer.toString();

			try
			{
				if (isAppend)
				{
					int offset = document.getLength();
					document.insertString(offset, line, attributes);
					textComponent.setCaretPosition( document.getLength() );
				}
				else
				{
					document.insertString(0, line, attributes);
					textComponent.setCaretPosition( 0 );
				}
			}
			catch (BadLocationException ble) {}

			if (printStream != null)
			{
				printStream.print(line);
			}

			buffer.setLength(0);
		}
	}
}



/*
 *  A class to control the maximum number of lines to be stored in a Document
 *
 *  Excess lines can be removed from the start or end of the Document
 *  depending on your requirement.
 *
 *  a) if you append text to the Document, then you would want to remove lines
 *     from the start.
 *  b) if you insert text at the beginning of the Document, then you would
 *     want to remove lines from the end.
 */
class LimitLinesDocumentListener implements DocumentListener
{
	private int maximumLines;
	private boolean isRemoveFromStart;

	/*
	 *  Specify the number of lines to be stored in the Document.
	 *  Extra lines will be removed from the start of the Document.
	 */
	public LimitLinesDocumentListener(int maximumLines)
	{
		this(maximumLines, true);
	}

	/*
	 *  Specify the number of lines to be stored in the Document.
	 *  Extra lines will be removed from the start or end of the Document,
	 *  depending on the boolean value specified.
	 */
	public LimitLinesDocumentListener(int maximumLines, boolean isRemoveFromStart)
	{
		setLimitLines(maximumLines);
		this.isRemoveFromStart = isRemoveFromStart;
	}

	/*
	 *  Return the maximum number of lines to be stored in the Document
	 */
	public int getLimitLines()
	{
		return maximumLines;
	}

	/*
	 *  Set the maximum number of lines to be stored in the Document
	 */
	public void setLimitLines(int maximumLines)
	{
		if (maximumLines < 1)
		{
			String message = "Maximum lines must be greater than 0";
			throw new IllegalArgumentException(message);
		}

		this.maximumLines = maximumLines;
	}

	//  Handle insertion of new text into the Document

	public void insertUpdate(final DocumentEvent e)
	{
		//  Changes to the Document can not be done within the listener
		//  so we need to add the processing to the end of the EDT

		SwingUtilities.invokeLater( new Runnable()
		{
			public void run()
			{
				removeLines(e);
			}
		});
	}

	public void removeUpdate(DocumentEvent e) {}
	public void changedUpdate(DocumentEvent e) {}

	/*
	 *  Remove lines from the Document when necessary
	 */
	private void removeLines(DocumentEvent e)
	{
		//  The root Element of the Document will tell us the total number
		//  of line in the Document.

		Document document = e.getDocument();
		Element root = document.getDefaultRootElement();

		while (root.getElementCount() > maximumLines)
		{
			if (isRemoveFromStart)
			{
				removeFromStart(document, root);
			}
			else
			{
				removeFromEnd(document, root);
			}
		}
	}

	/*
	 *  Remove lines from the start of the Document
	 */
	private void removeFromStart(Document document, Element root)
	{
		Element line = root.getElement(0);
		int end = line.getEndOffset();

		try
		{
			document.remove(0, end);
		}
		catch(BadLocationException ble)
		{
			System.out.println(ble);
		}
	}

	/*
	 *  Remove lines from the end of the Document
	 */
	private void removeFromEnd(Document document, Element root)
	{
		//  We use start minus 1 to make sure we remove the newline
		//  character of the previous line

		Element line = root.getElement(root.getElementCount() - 1);
		int start = line.getStartOffset();
		int end = line.getEndOffset();

		try
		{
			document.remove(start - 1, end - start);
		}
		catch(BadLocationException ble)
		{
			System.out.println(ble);
		}
	}
}