package ch.zhaw.mdp.fallstudie.jmail.ui.components;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JSplitPane;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.metal.MetalSplitPaneUI;

public class SolidJSplitPane extends JSplitPane {

	private static final long serialVersionUID = -5961879118051912892L;
	public static Color BORDER_COLOR = new Color(169, 183, 201);

	public SolidJSplitPane(int newOrientation, Component newLeftComponent, Component newRightComponent, double percent) {
		super(newOrientation, newLeftComponent, newRightComponent);

		setBorder(BorderFactory.createEmptyBorder());
		setOneTouchExpandable(true);

		setBackground(Color.RED);
		setForeground(Color.RED);
		setUI(new MetalSplitPaneUI() {
			@Override
			public BasicSplitPaneDivider createDefaultDivider() {
				BasicSplitPaneDivider divider = new BasicSplitPaneDivider(this);
				divider.setForeground(Color.RED);
				return divider;
			}
		});

		setDividerLocation(percent);
		setDividerSize(1);
	}
}
