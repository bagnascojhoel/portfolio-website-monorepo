<script>
  const TITLE = 'Hey, I am Jhoel';
  const DESCRIPTION = `
  I am a software engineer with more than 4 years of experience (January 2024), specialized in backend development.
  
  I have deep knowledge with Java and Spring for cloud-native HTTP/REST APIs, with production expertise in AWS, DDD, event-driven architecture, and CI/CD. But I also know my way around Python and Node.js back-end APIs as well as CI tools like Concourse and GitHub Actions.
  
  As a front-end developer, I exceed with React, Next.js, and Redux while keeping an organized, strongly typed, code base with TypeScript, ESLint, and Prettier. 
`;
 
  const USERNAME = '@bagnascojhoel';
  const BLOG_URL = 'https://blog.bagnascojhoel.com.br';
</script>

<section
  class="
    mb-16
  "
>
  <span class="text-primary-variant font-mono text-base">{USERNAME}</span>
  <h1
    class="
      text-primary
      mb-5
      leading-relaxed
      font-mono
      text-3xl
      lg:text-5xl
      font-medium
    "
  >
    {TITLE}
  </h1>
  <p class="text-base text-primary-variant max-w-3xl lg:max-w-lg mt-2 whitespace-pre-line">
    {DESCRIPTION}
  </p>
</section>
