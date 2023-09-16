workspace "portfolio-website-monorepo" {
   
   model {

      system = softwareSystem "portfolio-website" "All services that compose my portfolio-website" {
         
         notion = container "Notion API" "Used as a CMS for the Blog" "" external_service
         
         github = container "GitHub API" "Used to fetch the respositories that should be shown in the portfolio website" " " external_service
         
         bff = container "bff" "Handles GitHub authenticated requests and caching" "Spring" shape_backend_api,icon_spring_boot {
            -> github "Uses" "REST"
         }
         
         blog = container "blog" "Is a monolith with Next that fetches content from Notion.so" "Next" shape_frontend,icon_nextjs {
            -> notion "Uses" "REST"
         }
         
         front = container "frontend" "The UI for my portfolio-website. It only communicates with BFF" "Svelte" shape_frontend,icon_svelte {
            -> bff "Makes calls to" "REST"
            -> blog "Contains a link to" "REST"
         }
      }
      
   }
   
   views {
      container system {
        default
        title "System Overview"
        include *
        
      }
      
      styles {
         # Others
         element Element {
            metadata false
            background #FB923C
            color #F8FAFC
         }
         element external_service {
            background #B9BBBD
         }
         # Containers
         element shape_backend_api {
            shape "Hexagon"
            background #FB923C
         }
         element shape_frontend {
            shape "WebBrowser"
            background #FB923C
         }
         # Technologies
         element icon_nextjs {
            icon "theme/icon_nextjs.png"
         }
         element icon_spring_framework {
            icon "theme/icon_spring_framework.png"
         }
         element icon_spring_boot {
            icon "theme/icon_spring_boot.png"
         }
        element icon_svelte {
            icon "theme/icon_sveltejs.png"
        }

         ## Relationships
         relationship Relationship {
            color #F97316
            thickness 5
         }
      }
   }
}