package ch.zhaw.mdp.fallstudie.jmail.ui;

import java.awt.Color;
import java.awt.Component;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class MessageBox {

	public static final Color BACKGROUND_COLOR = new Color(238, 243, 250);
	public static final Color NODE_COLOR = new Color(0, 243, 250);

	private JTree tree;
	private final MessageViewer messageViewer;
	private DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("");
	/* DUMMY VALUES */
	private DefaultMutableTreeNode accountNode1 = new DefaultMutableTreeNode(
			"jmail@zhaw.ch");
	private DefaultMutableTreeNode inboxNode1 = new DefaultMutableTreeNode(
			"Inbox");
	private DefaultMutableTreeNode outboxNode1 = new DefaultMutableTreeNode(
			"Outbox");
	private DefaultMutableTreeNode trashNode1 = new DefaultMutableTreeNode(
			"Trash");
	private DefaultMutableTreeNode accountNode2 = new DefaultMutableTreeNode(
			"students@zhaw.ch");
	private DefaultMutableTreeNode inboxNode2 = new DefaultMutableTreeNode(
			"Inbox");
	private DefaultMutableTreeNode outboxNode2 = new DefaultMutableTreeNode(
			"Outbox");
	private DefaultMutableTreeNode trashNode2 = new DefaultMutableTreeNode(
			"Trash");

	public MessageBox(MessageViewer messageViewer) {
		this.messageViewer = messageViewer;

		rootNode.add(accountNode1);
		accountNode1.add(inboxNode1);
		accountNode1.add(outboxNode1);
		accountNode1.add(trashNode1);

		rootNode.add(accountNode2);
		accountNode2.add(inboxNode2);
		accountNode2.add(outboxNode2);
		accountNode2.add(trashNode2);

		tree = new JTree(rootNode);
		tree.setRootVisible(false);
		tree.setEditable(false);
		tree.setExpandsSelectedPaths(true);
		tree.setBackground(BACKGROUND_COLOR);
		tree.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) e
						.getPath().getLastPathComponent();
				Object userObject = node.getUserObject();
				// TODO: handle message box selection
			}
		});

		expandAll(tree, new TreePath(rootNode));
	}

	public JComponent getComponent() {
		return tree;
	}

	private void expandAll(JTree tree, TreePath parent) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration<?> e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path);
			}
		}
		tree.expandPath(parent);
	}
}
