# Estilização Minimalista do Sistema Alexandria

O sistema Alexandria agora usa um **tema minimalista moderno** inspirado no design system da Apple e Google Material Design, priorizando simplicidade, legibilidade e elegância.

## 🎨 Filosofia do Design

### Princípios
- **Simplicidade**: Interface limpa sem elementos desnecessários
- **Consistência**: Padrões visuais uniformes em todas as telas
- **Legibilidade**: Tipografia clara com hierarquia bem definida
- **Espaço**: Uso generoso de espaço em branco (whitespace)
- **Sutileza**: Efeitos visuais discretos e funcionais

## 🎨 Paleta de Cores Minimalista

### Cores Principais
- **Azul Sistema**: #007AFF (ações primárias)
- **Laranja**: #FF9500 (edição/avisos)
- **Vermelho**: #FF3B30 (ações destrutivas)
- **Verde**: #34C759 (sucesso/confirmação)

### Cores Neutras
- **Fundo Principal**: #fafafa (quase branco)
- **Superfície**: #ffffff (branco puro)
- **Texto Principal**: #333333 (quase preto)
- **Texto Secundário**: #666666 (cinza médio)
- **Texto Terciário**: #8E8E93 (cinza claro)
- **Bordas**: #e0e0e0 (cinza muito claro)

## �️ Tipografia

### Hierarquia
- **Título Principal**: 28px, peso 300 (ultraleve)
- **Título de Seção**: 20px, peso 300
- **Subtítulo**: 14px, peso 300
- **Texto Padrão**: 13px, peso 400 (normal)
- **Labels de Campo**: 13px, peso 400

### Fonte
Sistema: "Segoe UI", "San Francisco", "Helvetica Neue", "Arial"

## 🎛️ Componentes

### Botões
- **Primário**: Azul sistema (#007AFF)
- **Secundário**: Branco com borda cinza
- **Sucesso**: Verde (#34C759)
- **Perigo**: Vermelho (#FF3B30)
- **Aviso**: Laranja (#FF9500)

### Campos de Entrada
- Fundo branco com borda cinza clara (#d0d0d0)
- Borda azul no foco (#007AFF)
- Padding generoso (12px)
- Altura padrão: 36px
- Cantos arredondados: 6px

### Tabelas
- Header com fundo cinza claro (#f8f9fa)
- Linhas com divisórias sutis (#f0f0f0)
- Hover em cinza muito claro (#f8f9fa)
- Seleção em azul claro (#E3F2FD)

### Painéis (TitledPane)
- Fundo branco com borda cinza
- Título com fundo branco e divisória sutil
- Cantos arredondados: 8px
- Padding interno: 24px

## 📱 Layout e Espaçamento

### Espaçamentos
- **Container Principal**: 32px padding
- **Entre Seções**: 24px spacing
- **Entre Componentes**: 16px spacing
- **Entre Botões**: 12px spacing
- **Interno de Componentes**: 12px padding

### Menu e Navegação
- **MenuBar**: Fundo branco com borda inferior sutil
- **Tabs**: Fundo transparente, ativa com fundo branco
- **Tab Selecionada**: Borda superior e laterais

## Estrutura de Arquivos

```
microkernel/
├── app/src/main/resources/
│   ├── styles/alexandria-theme.css    # Tema minimalista
│   └── fxml/MainView.fxml             # Tela principal estilizada
└── plugins/*/src/main/resources/
    └── fxml/*View.fxml                # Telas dos plugins estilizadas
```

## 🎯 Exemplos de Uso

### Página Inicial
```xml
<VBox style="-fx-background-color: #fafafa; -fx-padding: 60;">
    <Label text="Alexandria" style="-fx-font-size: 28px; -fx-font-weight: 300; -fx-text-fill: #333333;" />
    <Label text="Sistema de Biblioteca" style="-fx-font-size: 14px; -fx-text-fill: #666666;" />
</VBox>
```

### Botões por Categoria
```xml
<!-- Botão Primário -->
<Button text="Salvar" style="-fx-background-color: #007AFF; -fx-text-fill: white;" />

<!-- Botão Secundário -->
<Button text="Cancelar" style="-fx-background-color: white; -fx-border-color: #d0d0d0;" />

<!-- Botão de Perigo -->
<Button text="Excluir" style="-fx-background-color: #FF3B30; -fx-text-fill: white;" />
```

### Campo de Entrada
```xml
<TextField style="-fx-background-color: white; -fx-border-color: #d0d0d0; -fx-border-radius: 6; -fx-padding: 12;" />
```

## Personalização

Para ajustar o tema, modifique as variáveis no início do CSS:

```css
:root {
    -fx-primary-color: #007AFF;      /* Azul do sistema */
    -fx-background-color: #fafafa;   /* Fundo principal */
    -fx-text-primary: #333333;      /* Texto principal */
    /* ... outras variáveis */
}
```
