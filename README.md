<a href="#">
  <p align="center">
    <img src="https://raw.githubusercontent.com/ZotyDev/Okaerinasai/main/branding/title.png" alt="Okaerinasai Title">
  </p>
</a>

A Minecraft `/home` Spigot plugin for 1.21, which has:
- `/home`
- `/warp`
- Cooldowns
- Defaults
- Particles
- Sounds
- Works across dimensions
- Database storage

# Setup

You need to configure your database credentials inside the `config.yml`.

> [!NOTE]
> To test the plugin, you can run the `compose.mysql.yml` database using `docker compose`. The access credentials are:
```yml
db-url: jdbc:mysql://localhost/okaerinasai
db-user: root
db-password: okaerinasai
```

> [!CAUTION]
> I do not recommend this plugin to be used on an important server, I just created it for a 5-day task, so I cannot guarantee it will be maintained.

# Commands

- `/home <home>` - teleports to your `<home>`, if `<home>` is not provided, the default home name is used
- `/homes` - lists your homes
- `/sethome <home>` - set a new home at the current location, if `<home>` is not provided, the default home name is used
- `/delhome <home>` - deletes a home, if `<home>` is not provided, the default home name is used
- `/warp <warp>` - teleports to `<warp>`, if `<warp>` is not provided, the default warp name is used
- `/warps` - list warps
- `/setwarp <warp>` - set a new warp at the current location, if `<warp>` is not provided, the default warp name is used
- `/delwarp <warp>` - deletes a warp, if `<warp>` is not provided, the default warp name is used

# Config

You can set the following values in the config:

## `home-cooldown`
Cooldown in seconds for `/home`, defaults to `5.0`.

## `warp-cooldown`
Cooldown in seconds for `/warp`, defaults to `10.0`.

## `particle`
Changes the particle that will spawn, defaults to `ENCHANT`.

## `particle-amount`
Changes the amount of particles that will spawn, defaults to `50`.

## `default-home`
Default name for `/home`, `/sethome` and `/delhome`, defaults to `home`.

## `default-warp`
Default name for `/warp`, `/setwarp` and `/delwarp`, defaults to `spawn`.

## `db-url`
The url to access the database, defaults to `default_db_url`. Example value: `jdbc:mysql://localhost/okaerinasai`

## `db-usr`
The database user, defaults to `default_db_user`.

## `db-password`
The database password, defaults to `default_db_password`.
