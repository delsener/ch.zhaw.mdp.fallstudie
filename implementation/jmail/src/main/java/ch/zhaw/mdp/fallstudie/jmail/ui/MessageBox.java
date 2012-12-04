package ch.zhaw.mdp.fallstudie.jmail.ui;

import java.awt.Color;
import java.util.Enumeration;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import ch.zhaw.mdp.fallstudie.jmail.core.account.Account;
import ch.zhaw.mdp.fallstudie.jmail.core.account.AccountUtil;

public class MessageBox {

	public static final Color BACKGROUND_COLOR = new Color(238, 243, 250);
	public static final Color NODE_COLOR = new Color(0, 243, 250);

	private final JTree tree;
	@SuppressWarnings("unused")
	private final MessageViewer messageViewer;
	private final DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("");

	public MessageBox(MessageViewer messageViewer) {
		this.messageViewer = messageViewer;

		this.reloadNodes();

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

	public void reloadNodes() {
		// load accounts
		this.rootNode.removeAllChildren();
		List<Account> accounts = AccountUtil.loadAccounts();
		for (Account account : accounts) {
			DefaultMutableTreeNode accountNode = new DefaultMutableTreeNode(account.getAccountName());
			DefaultMutableTreeNode inboxNode = new DefaultMutableTreeNode("Inbox");
			DefaultMutableTreeNode outboxNode = new DefaultMutableTreeNode("Outbox");
			DefaultMutableTreeNode trashNode = new DefaultMutableTreeNode("Trash");
			this.rootNode.add(accountNode);
			accountNode.add(inboxNode);
			accountNode.add(outboxNode);
			accountNode.add(trashNode);
		}

		if (this.tree != null) {
			this.tree.setModel(new DefaultTreeModel(this.rootNode));
			int row = 0;
			while (row < this.tree.getRowCount()) {
				this.tree.expandRow(row);
				row++;
			}
		}
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
