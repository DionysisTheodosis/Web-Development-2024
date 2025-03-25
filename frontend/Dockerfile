# Use node image to build the app
FROM node:18 AS build
WORKDIR /app
COPY ./frontend/medcare/package.json ./frontend/medcare/package-lock.json ./
RUN npm install
COPY ./frontend/medcare .
RUN npm run build  # This creates the build files (usually in the dist/ folder)

# Dockerfile (Nginx Setup)
# Use nginx to serve the React app
FROM nginx:alpine

# Copy the build files into the Nginx container
COPY --from=build /app/dist /usr/share/nginx/html

RUN  rm /etc/nginx/conf.d/default.conf
# Copy custom Nginx configuration
COPY ./frontend/nginx/nginx.conf /etc/nginx/nginx.conf
   
# Expose the port
EXPOSE 80

# Run Nginx
CMD ["nginx", "-g", "daemon off;"]