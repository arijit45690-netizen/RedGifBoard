curl -s "https://api.redgifs.com/v2/auth/temporary" > token.json
TOKEN=$(grep -o '"token":"[^"]*' token.json | cut -d'"' -f4)
curl -s -H "Authorization: Bearer $TOKEN" "https://api.redgifs.com/v2/gifs/search?search_text=trending&count=1" > gifs.json
cat gifs.json
