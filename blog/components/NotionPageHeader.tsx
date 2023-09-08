import * as React from 'react'

import * as types from 'notion-types'
import cs from 'classnames'
import { useNotionContext } from 'react-notion-x'

import {  navigationLinks, rootNotionPageId } from '@/lib/config'

import styles from './styles.module.css'
import { ReturnIcon } from './ReturnIcon'
import { useRouter } from 'next/router'

export function NotionPageHeader() {
  const { components, mapPageUrl } = useNotionContext()
  const router = useRouter()

  function isRootPath(): boolean {
    return router.pathname === '/' || router.basePath === router.pathname;
  }

  return (
    <header className='notion-header'>
      <div className='notion-nav-header'>

        <div className='notion-nav-header-rhs breadcrumbs'>
          {!isRootPath() && <div className={cs(styles.navLink, 'breadcrumb', 'button')} onClick={() => router.back()}>
            <ReturnIcon />
          </div>}

          {navigationLinks?.map((link, index) => {
              if (!link.pageId && !link.url) {
                return null
              }

              if (link.pageId) {
                return (
                  <components.PageLink
                    href={mapPageUrl(link.pageId)}
                    key={index}
                    className={cs(styles.navLink, 'breadcrumb', 'button')}
                  >
                    {link.title}
                  </components.PageLink>
                )
              } else {
                return (
                  <components.Link
                    href={link.url}
                    key={index}
                    className={cs(styles.navLink, 'breadcrumb', 'button')}
                  >
                    {link.title}
                  </components.Link>
                )
              }
            })
            .filter(Boolean)}
        </div>
      </div>
    </header>
  )
}
