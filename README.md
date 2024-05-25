# Battery Box

### Support

| Version | Modloader |  Status   |
|:-------:|:---------:|:---------:|
| 1.20.6  | NeoForge  |  Planned  |
| 1.20.4  | NeoForge  |  Planned  |
| 1.20.1  |   Forge   | Supported |

## About
Battery box is a "Mini-Mod" that does one simple thing, it adds a block that can hold a battery!\
Pairs perfectly with Just A Battery, but should work with other mod's battery cells as well!

![image](https://github.com/canitzp/Battery-Box/assets/68805162/304b6d18-47f8-4cac-b299-68791ff9ddb5) 
![image](https://github.com/canitzp/Battery-Box/assets/68805162/87381d0e-a9bb-420d-9776-6d55a6b75ebb)

Mod coding by **canitzp**\
Assets and Idea by **Jadan1213**

## Usage
Using the Battery box is simple, just connect it in-line between an RF power source and whatever you're powering.\
You can insert a battery by holding the item and right clicking on the block, shift right clicking will remove the item.

The rate of power flow into and out of the battery is regulated based on the RF transfer rate of the battery, and the pipes/energy cables you're connecting to it. You can hook up the Battery box from any side of the block.

This is great if your power usage temporarily exceeds your power generation capabilities, as the Battery box\
will provide the power your devices need and recharge when excess power is available.

![image](https://github.com/canitzp/Battery-Box/assets/68805162/e53ca5b5-58fb-4d34-a944-55d366104564)

The block will provide visual feedback of its charge level both with color, and with lighting. The higher the battery charge, the brighter the light!

|No Battery|Empty charge|Low charge|Medium charge|High/full charge|
|----------|------------|----------|-------------|----------------|
|![2024-05-24_16-16_Capture_541](https://github.com/canitzp/Battery-Box/assets/68805162/9bacd759-7325-4a2e-9144-3b6dd9ca7347)|![2024-05-24_16-14_Capture_540](https://github.com/canitzp/Battery-Box/assets/68805162/2585646b-f8ce-47e2-b3be-fc9fc1bb852e)|![2024-05-24_16-14_Capture_539](https://github.com/canitzp/Battery-Box/assets/68805162/e4c4954a-728c-4c66-9f04-c13cccc29363)|![2024-05-24_16-13_Capture_538](https://github.com/canitzp/Battery-Box/assets/68805162/1293f20b-acc4-47fe-9e21-bd27944a6433)|![2024-05-24_16-13_Capture_537](https://github.com/canitzp/Battery-Box/assets/68805162/3f37afcf-0247-4e92-b452-7a5b91111fc5)|

Battery boxes can share their battery's charge with adjacent blocks, even other Battery boxes! How fast that energy is transferred/leveled out will depend on the battery's max transfer speed.

![image](https://github.com/canitzp/Battery-Box/assets/68805162/ad68459b-ba4c-4ec4-8d5d-2cac5201c173)

While this will allow you to chain multiple Battery boxes together, Keep in mind, that energy will only flow into, and out of, a connected Battery box. If no battery is present in the connected Battery box, no energy will flow!

|No flow|Flow|Single input/output|Single input/Different output|Multiple input/Multiple output|
|-------|----|-------------------|--------------------------|------------------------------|
|![image](https://github.com/canitzp/Battery-Box/assets/68805162/8ddfeabe-124f-4018-924d-45644d772c85)|![image](https://github.com/canitzp/Battery-Box/assets/68805162/edf1ee58-1040-48a3-92c1-1665ac6054b3)|![image](https://github.com/canitzp/Battery-Box/assets/68805162/6e397ae2-79df-4249-99b8-1e356f657cd1)|![image](https://github.com/canitzp/Battery-Box/assets/68805162/073e9a68-93b4-45a0-a241-617005598d81)|![image](https://github.com/canitzp/Battery-Box/assets/68805162/a8d40d29-fa3d-4819-bef3-4c7cc6c6605b)|

Battery box is great for prividing extra power to hungry devices and storage systems when needed!

![image](https://github.com/canitzp/Battery-Box/assets/68805162/790544e0-71ba-42f6-af6e-9c271ee9d227)


|Tested to work with||
|-----------|-----------|
|Just a battery|Thermal series Flux capacitor|
|Powah! batteries & Energy cells|Mekanism energy tablet|



If you experience any issues or incompatabilities, please open an Issue!
