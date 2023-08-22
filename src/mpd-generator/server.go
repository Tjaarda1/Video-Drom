package main

import (
	"fmt"
	"io"
	"net/http"
	"os"
	"os/exec"
)

func main() {
	fmt.Print("Starting server")
	http.HandleFunc("/upload", uploadHandler)
	http.ListenAndServe(":8083", nil)
}

func uploadHandler(w http.ResponseWriter, r *http.Request) {
	fmt.Print("Received request!")

	if r.Method != http.MethodPost {
		http.Error(w, "Only POST requests are allowed", http.StatusMethodNotAllowed)
		return
	}

	err := r.ParseMultipartForm(10 << 20) // 10 MB limit
	if err != nil {
		fmt.Println(err)

		http.Error(w, "Unable to parse form", http.StatusInternalServerError)
		return
	}

	title := r.FormValue("title")

	fmt.Print(title)
	videoFile, videoHeader, err := r.FormFile("video")

	if err != nil {
		fmt.Println(err)

		http.Error(w, "Error retrieving video file", http.StatusBadRequest)

		return
	}

	defer videoFile.Close()

	// thumbnailFile, thumbnailHeader, err := r.FormFile("thumbnail")
	// if err != nil {
	// 	fmt.Printf("error: %v", err)

	// 	http.Error(w, "Error retrieving thumbnail file", http.StatusBadRequest)
	// 	return
	// }
	// defer thumbnailFile.Close()

	// Create directories if they don't exist
	os.MkdirAll("videos", os.ModePerm)

	dir, _ := os.Getwd()
	fmt.Print(dir)
	// Create video and thumbnail files
	videoPath := fmt.Sprintf("videos/%s", videoHeader.Filename)
	//thumbnailPath := fmt.Sprintf("thumbnails/%s", thumbnailHeader.Filename)

	fmt.Print(videoPath)

	videoOutput, err := os.Create(videoPath)
	if err != nil {
		fmt.Println(err)
		http.Error(w, "Error creating video file", http.StatusInternalServerError)
		return
	}
	defer videoOutput.Close()

	//thumbnailOutput, err := os.Create(thumbnailPath)
	// if err != nil {
	// 	http.Error(w, "Error creating thumbnail file", http.StatusInternalServerError)
	// 	return
	// }
	// defer thumbnailOutput.Close()

	// Copy uploaded files to destination
	_, err = io.Copy(videoOutput, videoFile)
	if err != nil {
		fmt.Println(err)

		http.Error(w, "Error copying video file", http.StatusInternalServerError)
		return
	}

	// _, err = io.Copy(thumbnailOutput, thumbnailFile)
	// if err != nil {
	// 	http.Error(w, "Error copying thumbnail file", http.StatusInternalServerError)
	// 	return
	// }

	// You can implement your processing logic here using the videoPath and thumbnailPath
	// For example, you might want to call a function that processes the files.

	fmt.Print("all good")

	cmd, err := exec.Command("bash", "generate_mpd.sh", videoHeader.Filename).Output()
	if err != nil {
		fmt.Printf("error %s", err)
	}
	output := string(cmd)

	fmt.Println(output)
}
