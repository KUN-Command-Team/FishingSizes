<img width="1920" height="1009" alt="2026-03-24_17 51 58" src="https://github.com/user-attachments/assets/72749042-f8e8-4f69-bacf-5869977e0c3f" />

釣りあげたアイテムに大きさのNBTタグとLoreを追加するMODです。
大きさの最小値と最大値の定義はデータパックで変更可能です。

---

データパックによる制御
1. `data/fishingsizes/` 下に `fish_sizes.json`を作成する
2. デフォルトの `fish_sizes.json` は以下の通りです。
    ```JSON:fish_sizes.json
    {
      "replace": false,
      "sizes": {
        "minecraft:cod": {
          "min": 10.0,
          "max": 30.0
        },
        "minecraft:salmon": {
          "min": 10.0,
          "max": 30.0
        },
        "minecraft:tropical_fish": {
          "min": 10.0,
          "max": 30.0
        },
        "minecraft:pufferfish": {
          "min": 10.0,
          "max": 30.0
        }
      }
    }
    ```
