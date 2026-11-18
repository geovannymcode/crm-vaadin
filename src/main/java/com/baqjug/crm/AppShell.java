package com.baqjug.crm;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.ColorScheme;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.theme.aura.Aura;

@StyleSheet(Aura.STYLESHEET)
@StyleSheet("styles.css") // Your custom styles
@ColorScheme(ColorScheme.Value.DARK)
@Push
public class AppShell implements AppShellConfigurator {
}
