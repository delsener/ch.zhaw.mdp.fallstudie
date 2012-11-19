package ch.zhaw.mdp.fallstudie.jmail.ui;

import java.awt.Color;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class MessageBox {

	public static final Color BACKGROUND_COLOR = new Color(238, 243, 250);
	public static final Color NODE_COLOR = new Color(0, 243, 250);

	private final JTree tree;
	@SuppressWarnings("unused")
	private final MessageViewer messageViewer;
	private final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("");
	/* DUMMY VALUES */
	private final DefaultMutableTreeNode accountNode1 = new DefaultMutableTreeNode("jmail@zhaw.ch");
	private final DefaultMutableTreeNode inboxNode1 = new DefaultMutableTreeNode("Inbox");
	private final DefaultMutableTreeNode outboxNode1 = new DefaultMutableTreeNode("Outbox");
	private final DefaultMutableTreeNode trashNode1 = new DefaultMutableTreeNode("Trash");
	private final DefaultMutableTreeNode accountNode2 = new DefaultMutableTreeNode("students@zhaw.ch");
	private final DefaultMutableTreeNode inboxNode2 = new DefaultMutableTreeNode("Inbox");
	private final DefaultMutableTreeNode outboxNode2 = new DefaultMutableTreeNode("Outbox");
	private final DefaultMutableTreeNode trashNode2 = new DefaultMutableTreeNode("Trash");

	public MessageBox(MessageViewer messageViewer) {
		this.messageViewer = messageViewer;

		this.rootNode.add(this.accountNode1);
		this.accountNode1.add(this.inboxNode1);
		this.accountNode1.add(this.outboxNode1);
		this.accountNode1.add(this.trashNode1);

		this.rootNode.add(this.accountNode2);
		this.accountNode2.add(this.inboxNode2);
		this.accountNode2.add(this.outboxNode2);
		this.accountNode2.add(this.trashNode2);

		this.tree = new JTree(this.rootNode);
		this.tree.setRootVisible(false);
		this.tree.setEditable(false);
		this.tree.setExpandsSelectedPaths(true);
		this.tree.setBackground(MessageBox.BACKGROUND_COLOR);
		this.tree.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();
				@SuppressWarnings("unused")
				Object userObject = node.getUserObject();
				// TODO: handle message box selection
			}
		});

		this.expandAll(this.tree, new TreePath(this.rootNode));
	}

	public JComponent getComponent() {
		return this.tree;
	}

	private void expandAll(JTree tree, TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				this.expandAll(tree, path);
			}
		}
		tree.expandPath(parent);
	}
}
