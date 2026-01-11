# Adventure Mode Adjust

Configure which items and blocks can be placed and broken in Adventure mode using regular expressions.

Normally, players in Adventure mode cannot place or break ANY blocks, but with this mod, you can customize that behavior. The configuration supports regex patterns, allowing for flexible and powerful definitions of what can be interacted with. For example, here is the default configuration:

```toml
[canPlaceOn]
    "ladder|torch|tnt" = ".*"
    "crafting_table|furnace|campfire" = "grass_block|stone|deepslate"
    "obsidian" = "obsidian"
    "ender_eye" = "end_portal_frame"

[canBreak]
    ".*" = "ladder|torch|wall_torch|.*_log|.*_ore|obsidian|netherrack|end_stone"
```

The mod has to be present on both the server and the client with the same configuration to work properly.

![Logo](/images/logo.png)


## Credits

Project template used: https://github.com/jaredlll08/MultiLoader-Template
