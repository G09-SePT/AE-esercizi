	/*
	 *
	 *  based on gijvmasm.java
	 *
	 *  mic1 microarchitecture simulator
	 *  Copyright (C) 1999, Prentice-Hall, Inc.
	 *
	 *  This program is free software; you can redistribute it and/or modify
	 *  it under the terms of the GNU General Public License as published by
	 *  the Free Software Foundation; either version 2 of the License, or
	 *  (at your option) any later version.
	 *
	 *  This program is distributed in the hope that it will be useful, but
	 *  WITHOUT ANY WARRANTY; without even the implied warranty of
	 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
	 *  Public License for more details.
	 *
	 *  You should have received a copy of the GNU General Public License along with
	 *  this program; if not, write to:
	 *
	 *    Free Software Foundation, Inc.
	 *    59 Temple Place - Suite 330
	 *    Boston, MA 02111-1307, USA.
	 *
	 *  A copy of the GPL is available online the GNU web site:
	 *
	 *    http://www.gnu.org/copyleft/gpl.html
	 *
	 */

	import java.awt.BorderLayout;
	import java.awt.Button;
	import java.awt.Event;
	import java.awt.FileDialog;
	import java.awt.FlowLayout;
	import java.awt.Frame;
	import java.awt.GridLayout;
	import java.awt.Label;
	import java.awt.Panel;
	import java.awt.TextArea;
	import java.awt.TextField;
	import java.io.BufferedInputStream;
	import java.io.FileInputStream;
	import java.io.FileNotFoundException;
	import java.io.FileOutputStream;
	import java.io.IOException;
	import java.io.InputStream;
	import java.io.OutputStream;
	import java.io.PrintStream;


	public class IJVMcompiler extends Frame
	{

		private Panel
				label_panel,
				text_panel,
				browse_panel,
				stuff_panel,
				button_panel;
		private Button
				compile_button,
				cancel_button,
				in_button,
				out_button;
		private TextField
				in_text,
				out_text;
		private Label msg;
		private PrintStream err;
		private boolean on = true;

		public IJVMcompiler()
		{
			super("IJVM Assembler");

			label_panel = new Panel();
			label_panel.setLayout(new GridLayout(2, 1, 10, 10));
			label_panel.add(new Label("Input file"));
			label_panel.add(new Label("Output file"));

			stuff_panel = new Panel();
			stuff_panel.setLayout(new BorderLayout());
			text_panel = new Panel();
			in_text = new TextField(30);
			out_text = new TextField(30);
			in_text.setEditable(true);
			out_text.setEditable(true);
			in_text.setText("");
			text_panel.setLayout(new GridLayout(2, 1, 10, 10));
			text_panel.add(in_text);
			text_panel.add(out_text);

			browse_panel = new Panel();
			browse_panel.setLayout(new GridLayout(2, 1, 10, 10));
			browse_panel.add(in_button = new Button("Find input file..."));
			browse_panel.add(out_button = new Button("Find output file..."));

			button_panel = new Panel();
			//button_panel.setLayout(new GridLayout(1,2,5,5));
			button_panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			button_panel.add(compile_button = new Button("Compile"));
			button_panel.add(cancel_button = new Button("Exit"));
			msg = new Label();
			stuff_panel.add("West", label_panel);
			stuff_panel.add("Center", text_panel);
			stuff_panel.add("East", browse_panel);
			stuff_panel.add("South", button_panel);
			TextArea errConsole = new TextArea();
			errConsole.setEditable(false);

			err = new PrintStream(new TextAreaOutputStream(errConsole));


			add("South", errConsole);

			add("North", stuff_panel);
			add("Center", msg);
			msg.setText("Ready");
			setVisible(true);
			setSize(getPreferredSize());
			paintAll(getGraphics());
		}

		public boolean isOn()
		{
			return on;
		}

		private void compile()
		{
			InputStream in = null;
			OutputStream out = null;
			IJVMAssembler ia = null;
			String infile = in_text.getText().trim();
			String outfile = out_text.getText().trim();
			if (infile.equals(""))
			{
				msg.setText("Source file must be given");
				err.println("Source file must be given");
				return;
			} else if (!infile.endsWith(".jas"))
			{
				msg.setText("Source file must be .jas file");
				err.println("Source file must be .jas file");

				return;
			}
			if (outfile == null || outfile.trim().equals(""))
			{
				outfile = infile.substring(0, infile.length() - 4) + ".ijvm";
			}
			if (!outfile.endsWith(".ijvm"))
			{
				msg.setText("Destination file must be .ijvm file");
				err.println("Destination file must be .ijvm file");
				return;
			} else
			{
				try
				{
					in = new BufferedInputStream(new FileInputStream(infile));
				} catch (FileNotFoundException fnfe)
				{
					msg.setText("File not found: " + infile + ", unable to compile");
					err.println("File not found: " + infile + ", unable to compile");
					return;
				} catch (Exception ex)
				{
					msg.setText("Error opening file: " + infile + ", unable to compile");
					err.println("Error opening file: " + infile + ", unable to compile");
					return;
				}
				try
				{
					out = new FileOutputStream(outfile);
				} catch (IOException ioe)
				{
					msg.setText("Error opening file: " + outfile + ", unable to compile");
					err.println("Error opening file: " + outfile + ", unable to compile");
					return;
				}
				msg.setText("Compiling " + infile + "...");
				err.println("Compiling " + infile + "...");
				ia = new IJVMAssembler(in, out, outfile, err);
				try
				{
					in.close();
					out.close();
				} catch (IOException e)
				{
					err.println(e);
				}

				if (ia.getStatus())
				{
					msg.setText("Compilation successfull");
					err.println(" Compilation successfull");
				} else
				{
					msg.setText("Error compiling file: " + infile);
					err.println("Error compiling file: " + infile);
				}
			}
		}

		private void getInfile()
		{
			FileDialog fd = new FileDialog(this, "Select infile", FileDialog.LOAD);
			fd.setFile("*.jas");
			fd.setDirectory(".");
			fd.setVisible(true);
			if (fd.getFile() != null)
			{
				in_text.setText(fd.getDirectory() + fd.getFile());
			}
		}


		private void getOutfile()
		{
			FileDialog fd = new FileDialog(this, "Select outfile", FileDialog.LOAD);
			fd.setFile("*.ijvm");
			fd.setDirectory(".");
			fd.setVisible(true);
			if (fd.getFile() != null)
			{
				out_text.setText(fd.getDirectory() + fd.getFile());
			}
		}

		public boolean handleEvent(Event event)
		{
			switch (event.id)
			{
				case Event.ACTION_EVENT:
					if (event.target == compile_button)
					{
						compile();
						return true;
					} else if (event.target == cancel_button)
					{
						on = false;
						this.dispose();
						return true;
					} else if (event.target == in_button)
					{
						getInfile();
						return true;
					} else if (event.target == out_button)
					{
						getOutfile();
						return true;
					}
					break;
				case Event.WINDOW_DESTROY:
					on = false;
					this.dispose();
					break;
			}
			return false;
		}
	}

