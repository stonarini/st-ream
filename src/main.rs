use std::fs;
use rocket::serde::{Serialize, json::Json};
use rocket::tokio::fs::File;
use rocket::response::stream::ReaderStream;

#[macro_use] extern crate rocket;

#[derive(Serialize)]
#[serde(crate = "rocket::serde")]
struct Song {
    id: i32,
    name: String,
    artist: String,
}


#[derive(Serialize)]
#[serde(crate = "rocket::serde")]
struct Songs {
    songs: Vec<Song>,
}

#[get("/list")]
fn list() -> Json<Vec<Song>> {
    let paths = fs::read_dir("./audio").unwrap();
    let mut songs = Vec::new();
    let mut curr_id = 0;
    for path in paths {
        curr_id += 1;
        let name = path.unwrap().path().file_name().unwrap().to_str().unwrap().to_string();
        songs.push(Song { id: curr_id, name: name.replace("-", " ")[0..name.len() - 4].to_string(), artist: "Artist".to_string() });
    }
    Json(songs)
}

#[get("/stream/<id>")]
async fn stream_one(id: &str) -> std::io::Result<ReaderStream![File]> {
    let paths = fs::read_dir("./audio").unwrap();
    let mut curr_id = 0;
    for path in paths {
        curr_id += 1;
        if curr_id == id.parse::<i32>().unwrap() {
            let file = File::open(path.unwrap().path()).await?;
            return Ok(ReaderStream::one(file));
        }
    }
    Ok(ReaderStream::one(File::open("../Cargo.toml").await?))
}

#[launch]
fn rocket() -> _ {
    rocket::build().mount("/", routes![list, stream_one])
}
