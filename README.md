# Video Upload - Docker notes

Quick steps (Windows cmd) to build and run locally:

1. Build the image:

```cmd
cd /d d:\SpringBoot\upload
docker build -t uploader .
```

2. Run the container (pass Cloudinary creds via env):

```cmd
docker run --rm -p 8080:8080 -e CLOUDINARY_NAME=your_name -e CLOUDINARY_API_KEY=your_key -e CLOUDINARY_API_SECRET=your_secret uploader
```

3. Or use docker-compose (copy `.env.example` to `.env` first):

```cmd
copy .env.example .env
rem # edit .env with your values
docker-compose up --build
```

Notes:
- The Dockerfile is a multi-stage build that compiles the JAR inside the image using the Maven wrapper.
- Use `mvn -DskipTests package` locally if you prefer to build outside Docker and then run the jar.
